package com.soomgo.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.soomgo.myapplication.databinding.ActivityMainBinding
import com.soomgo.myapplication.ui.detail.DetailActivity
import com.soomgo.myapplication.ui.fragment.FragmentActivity
import kotlinx.android.parcel.Parcelize
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TAG", "onCreate")

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
            startNewActivity.setOnClickListener {
                Intent(this@MainActivity, DetailActivity::class.java).apply {
                    startActivity(this)
                }
            }

            startNewActivityForResult.setOnClickListener {
                Intent(this@MainActivity, DetailActivity::class.java).apply {
                    //todo deprecated?!
                    startActivityForResult(this, REQUEST_CODE)
                }
            }

            startNewFragment.setOnClickListener {
                Intent(this@MainActivity, FragmentActivity::class.java).apply {
                    putExtra(FRAGMENT_TYPE, 0)
                    startActivity(this)
                }
            }

            startGithubFragment.setOnClickListener {
                Intent(this@MainActivity, FragmentActivity::class.java).apply {
                    putExtra(FRAGMENT_TYPE, 1)
                    startActivity(this)
                }
            }

            startCoroutinesGithubFragment.setOnClickListener {
                Intent(this@MainActivity, FragmentActivity::class.java).apply {
                    putExtra(FRAGMENT_TYPE, 2)
                    startActivity(this)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE -> {
                data?.let {
                    val result = it.getStringExtra(RESULT_TITLE)
                    result.takeIf { it?.isNotEmpty() ?: false }?.let {
                        Toast.makeText(this, "$result", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putParcelable("asdf", TEST("aa", "bb"))
            putString(KEY_DATA, mainViewModel.count.value.toString())
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val data = savedInstanceState.getString(KEY_DATA)
        Toast.makeText(this@MainActivity, "restore data? $data", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Log.i("TAG", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("TAG", "onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("TAG", "onDestroy")

    }

    override fun onStart() {
        super.onStart()
        Log.i("TAG", "onStart")

    }

    companion object {
        const val KEY_DATA = "KEY_DATA"
        const val REQUEST_CODE = 1001
        const val RESULT_TITLE = "RESULT_TITLE"
        const val FRAGMENT_TYPE = "FRAGMENT_TYPE"
    }
}

/**
 * Activity life cycler
 *
 * onCreate()
 *      You must implement this callback
 *      In the onCreate() method, you perform basic application startup logic that should happen
 *      only once for the entire life of the activity.
 *      For example, your implementation of onCreate() might bind data to lists,
 *      associate the activity with a ViewModel, and instantiate some class-scope variables
 *
 * onStart()
 *      The onStart() call makes the activity visible to the user,
 *      as the app prepares for the activity to enter the foreground and become interactive.
 *
 * onResume()
 *      This is the state in which the app interacts with the user
 *
 * onPause()
 *      The system calls this method as the first indication that
 *      the user is leaving your activity (though it does not always mean the activity is being destroyed);
 *      You can also use the onPause() method to release system resources,
 *      handles to sensors (like GPS), or any resources that
 *      may affect battery life while your activity is paused and the user does not need them
 * onStop()
 *      When your activity is no longer visible to the user,
 *      it has entered the Stopped state, and the system invokes the onStop() callback
 * onDestroy()
 *      onDestroy() is called before the activity is destroyed. The system invokes this callback either because:
 *      the activity is finishing
 *          (due to the user completely dismissing the activity or due to finish() being called on the activity), or
 *      the system is temporarily destroying the activity
 *          due to a configuration change (such as device rotation or multi-window mode)
 * */


@Parcelize
data class TEST(val id: String, val name: String) : Parcelable // add missing parcelable implement

/**
 * todo
 * week2
 * parcelize //done
 * startactivityforresult
 *
 *
 * */


