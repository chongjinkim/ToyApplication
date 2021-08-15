package com.soomgo.myapplication.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.soomgo.myapplication.R
import com.soomgo.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val mainViewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.apply {
            lifecycleOwner = this@MainActivity
            viewModel = mainViewModel
            minus.setOnClickListener {
                mainViewModel.decrease()
            }
            add.setOnClickListener {
                mainViewModel.increase()
            }
        }

//        binding.lifecycleOwner = this
//        binding.viewModel = viewModel
//
//        binding.minus.setOnClickListener {
//            viewModel.decrease()
//        }
//
//        binding.add.setOnClickListener {
//            viewModel.increase()
//        }
    }
}