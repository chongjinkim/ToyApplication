package com.soomgo.myapplication.data.network

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.platform.Platform
import okio.Buffer
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class HttpCustomLoggingInterceptor constructor(private val logger: Logger = Logger.DEFAULT) :
    Interceptor {
    private val UTF8 = Charset.forName("UTF-8")

    enum class Level {
        NONE,
        BASIC,
        HEADERS,
        BODY
    }

    interface Logger {
        fun log(message: String)
        fun log(level: Level, message: String)

        companion object {
            /** A [Logger] defaults output appropriate for the current platform.  */
            val DEFAULT: Logger = object : Logger {
                override fun log(level: Level, message: String) = log(message)
                override fun log(message: String) {
                    Platform.get().log(message, Platform.INFO, null)
                }
            }
        }
    }

    @Volatile
    var level = Level.NONE

    /** Change the level at which this interceptor logs.  */
    fun setLevel(level: Level?): HttpCustomLoggingInterceptor {
        if (level == null) throw NullPointerException("level == null. Use Level.NONE instead.")
        this.level = level
        return this
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val level = this.level

        val request = chain.request()
        if (level == Level.NONE) {
            return chain.proceed(request)
        }

        val logBody = level == Level.BODY
        val logHeaders = logBody || level == Level.HEADERS

        val requestBody = request.body
        val hasRequestBody = requestBody != null

        val connection = chain.connection()
        var requestStartMessage =
            ("--> " + request.method + ' '.toString() + request.url + if (connection != null) " " + connection.protocol() else "")
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody!!.contentLength() + "-byte body)"
        }
        logger.log(Level.HEADERS, requestStartMessage)

        if (logHeaders) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody!!.contentType() != null) {
                    logger.log(Level.HEADERS, "Content-Type: " + requestBody.contentType()!!)
                }

                if (requestBody.contentLength() != -1L) {
                    logger.log(Level.HEADERS, "Content-Length: " + requestBody.contentLength())
                }
            }

            val headers = request.headers
            var i = 0
            val count = headers.size

            while (i < count) {
                val name = headers.name(i)
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equals(name, ignoreCase = true) && !"Content-Length".equals(
                        name,
                        ignoreCase = true
                    )
                ) {
                    logger.log(Level.HEADERS, name + ": " + headers.value(i))
                }
                i++
            }

            var isJsonType = false

            requestBody?.contentType()?.subtype?.let {
                if (it.equals("json", true)) {
                    isJsonType = true
                }
            }

            if (!logBody || !hasRequestBody) {
                logger.log(Level.HEADERS, "--> END " + request.method)
            } else if (bodyEncoded(request.headers)) {
                logger.log(Level.HEADERS, "--> END " + request.method + " (encoded body omitted)")
            } else {
                val buffer = Buffer()

                if (isJsonType) {
                    requestBody?.writeTo(buffer)
                }

                var charset: Charset? = UTF8
                val contentType = requestBody?.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }

                if (isPlaintext(buffer)) {
                    if (isJsonType) {
                        logger.log(Level.HEADERS, buffer.readString(charset!!))
                        logger.log(
                            Level.HEADERS,
                            """--> END ${request.method} (${requestBody?.contentLength()}-byte body)"""
                        )
                    }
                } else {
                    logger.log(
                        Level.HEADERS,
                        "--> END " + request.method + " (binary " + requestBody?.contentLength() + "-byte body omitted)"
                    )
                }
            }
        }

        val startNs = System.nanoTime()
        val response: Response

        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            logger.log(Level.BODY, "<-- HTTP FAILED: $e")
            throw e
        }

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body
        val contentLength = responseBody!!.contentLength()
        val bodySize = if (contentLength != -1L) "$contentLength-byte" else "unknown-length"

        logger.log(
            Level.BODY,
            "<-- " + response.code + (if (response.message.isEmpty()) "" else ' ' + response.message) + ' '.toString() + response.request.url + " (" + tookMs + "ms" + (if (!logHeaders) ", $bodySize body" else "") + ')'.toString()
        )

        if (logHeaders) {
            val headers = response.headers
            var i = 0
            val count = headers.size
            while (i < count) {
                logger.log(Level.HEADERS, headers.name(i) + ": " + headers.value(i))
                i++
            }

            //if (!logBody || !HttpHeaders.hasBody(response)) {
            if (!logBody) {
                logger.log(Level.BODY, "<-- END HTTP")
            } else if (bodyEncoded(response.headers)) {
                logger.log(Level.BODY, "<-- END HTTP (encoded body omitted)")
            } else {
                //val buffer = createBuffer(responseBody).workOnSchedulerIo().blockingGet()
                val source = responseBody.source()
                source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
                val buffer = source.buffer

                var charset: Charset? = UTF8
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }

                if (!isPlaintext(buffer)) {
                    logger.log("")
                    logger.log(
                        Level.BODY,
                        "<-- END HTTP (binary " + buffer.size + "-byte body omitted)"
                    )
                    return response
                }

                if (contentLength != 0L) {
                    logger.log("")
                    val body = buffer.clone().readString(charset!!)
                    logger.log(Level.BODY, toPrettyFormat(body))
                }

                logger.log(Level.BODY, "<-- END HTTP (" + buffer.size + "-byte body)")
            }
        }
        return response
    }

    private fun toPrettyFormat(jsonString: String): String {
        return try {
            GsonBuilder().setPrettyPrinting().create()
                .toJson(JsonParser.parseString(jsonString).asJsonObject)
        } catch (e: IllegalStateException) {
            GsonBuilder().setPrettyPrinting().create()
                .toJson(JsonParser.parseString(jsonString).asJsonArray)
        } catch (e: java.lang.Exception) {
            "[Parsing Exception - $e:${e.message}]\n$jsonString"
        }
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private fun isPlaintext(buffer: Buffer): Boolean {
        try {
            val prefix = Buffer()
            val byteCount = if (buffer.size < 64) buffer.size else 64
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            return true
        } catch (e: EOFException) {
            return false // Truncated UTF-8 sequence.
        }
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers.get("Content-Encoding")
        return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
    }

}