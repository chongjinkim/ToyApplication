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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fragment)
        setClickListener()
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, MainFragment.newInstance())
            .commit()
    }

    private fun setClickListener() {
        binding.changeFragment.setOnClickListener {
            viewModel.setContents("sent from blank")
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, DetailFragment.newInstance())
                .addToBackStack(null)//back stack 추가 필요
                .commit()
        }
    }
}