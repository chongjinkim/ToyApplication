package com.soomgo.myapplication.ui.fragment

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.soomgo.myapplication.R
import com.soomgo.myapplication.databinding.ActivityFragmentBinding

class FragmentActivity : AppCompatActivity() {
    lateinit var binding: ActivityFragmentBinding
    val viewModel: SharedViewModel by viewModels()
    private val mainClickListener: (String) -> Int = {title->
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, DetailFragment.newInstance(title))
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fragment)
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, MainFragment.newInstance().apply {
                this.clickListener = mainClickListener
            })
            .commit()
    }
}