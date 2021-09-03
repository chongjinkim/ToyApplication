package com.soomgo.myapplication.ui.github

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ListAdapter
import com.soomgo.myapplication.data.model.User
import com.soomgo.myapplication.data.model.UserResponse
import com.soomgo.myapplication.databinding.FragmentCoroutineListBinding
import com.soomgo.myapplication.databinding.FragmentMainBinding
import com.soomgo.myapplication.databinding.LayoutMainListBinding
import com.soomgo.myapplication.ui.fragment.MainFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoroutineGithubListFragment : Fragment() {
    lateinit var binding: FragmentCoroutineListBinding
    val githubAdapter = GithubListFragment.GithubAdapter()
    val viewModel: CoroutineGithubViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCoroutineListBinding.inflate(
        inflater, container, false
    ).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {
        binding.apply {
            editText.addTextChangedListener {
                viewModel.setQuery(it.toString())
            }

            recyclerview.apply {
                adapter = githubAdapter
            }
        }
    }

    private fun observe() {
        viewModel.list.observe(viewLifecycleOwner) {
            githubAdapter.submitList(it.items)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String = "", param2: String = "") = CoroutineGithubListFragment()
    }
}