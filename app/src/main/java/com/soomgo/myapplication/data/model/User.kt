package com.soomgo.myapplication.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int,
    val avatar_url: String,
    val events_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val html_url: String,
    val login: String,
    val node_id: String,
    val organizations_url: String,
    val received_events_url: String,
    val repos_url: String,
    val score: Int,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    val url: String,
    var isFavorite: Boolean
) : Parcelable {
    //한 곳에서 관리할 수 있는 장점
    //서버에서 가지고 온 비교할 수 있는 기준
    companion object {
        val DiffUtil = object : androidx.recyclerview.widget.DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem == newItem
            override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
        }
    }
}

data class UserResponse(
    val items: List<User> = emptyList()
)