package com.soomgo.myapplication.ui.fragment

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.soomgo.myapplication.MainActivity.Companion.FRAGMENT_TYPE
import com.soomgo.myapplication.R
import com.soomgo.myapplication.databinding.ActivityFragmentBinding
import com.soomgo.myapplication.ui.github.CoroutineGithubListFragment
import com.soomgo.myapplication.ui.github.GithubListFragment
import java.lang.IllegalArgumentException

class FragmentActivity : AppCompatActivity() {
    lateinit var binding: ActivityFragmentBinding
    val viewModel: SharedViewModel by viewModels()
    private val mainClickListener: (String) -> Int = { title ->
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, DetailFragment.newInstance(title))
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fragment)
        val fragmentType = intent.getIntExtra(FRAGMENT_TYPE,0)
        initFragment(fragmentType)
    }

    private fun initFragment(fragmentType: Int) {
        val fragment = when(fragmentType){
            0 -> MainFragment.newInstance()
            1 -> GithubListFragment.newInstance()
            2 -> CoroutineGithubListFragment.newInstance()
            else -> throw IllegalArgumentException("not defined fragment")
        }
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, fragment)
            .commit()
    }
}