package com.soomgo.myapplication.ui.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.soomgo.myapplication.databinding.FragmentCoroutineListBinding
import kotlinx.coroutines.launch

class TestFragment : Fragment(){
    lateinit var  binding : FragmentCoroutineListBinding
    val adapter = GithubAdapter()
    val viewModel by viewModels<CoroutineGithubListViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCoroutineListBinding.inflate(
        inflater, container, false
    ).apply{
        lifecycleOwner = viewLifecycleOwner
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getUser()
        }

        viewModel.list.observe(viewLifecycleOwner){
            adapter.submitList(it.items)
        }
    }
}