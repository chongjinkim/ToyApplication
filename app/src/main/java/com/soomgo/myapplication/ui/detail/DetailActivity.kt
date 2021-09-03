package com.soomgo.myapplication.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.soomgo.myapplication.R
import com.soomgo.myapplication.databinding.ActivityDetailBinding
import com.soomgo.myapplication.MainActivity.Companion.REQUEST_CODE
import com.soomgo.myapplication.MainActivity.Companion.RESULT_TITLE

class DetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        binding.apply {
            lifecycleOwner = this@DetailActivity
        }
    }

    override fun onResume() {
        super.onResume()
        binding.finish.setOnClickListener {
            setResult(REQUEST_CODE, Intent().apply { putExtra(RESULT_TITLE,"back from detail") })
            finish()
        }
    }
}