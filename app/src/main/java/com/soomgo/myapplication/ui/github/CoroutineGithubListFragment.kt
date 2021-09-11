package com.soomgo.myapplication.ui.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.soomgo.myapplication.R
import com.soomgo.myapplication.databinding.FragmentCoroutineListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoroutineGithubListFragment : Fragment() {
    lateinit var binding: FragmentCoroutineListBinding
    val githubAdapter = GithubAdapter()
    val viewModel: CoroutineGithubListViewModel by viewModel()
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
                adapter = githubAdapter.apply {
                    clickListener = { user ->
                        val fragment = GithubDetailFragment.newInstance(user)
                        activity
                            ?.supportFragmentManager
                            ?.beginTransaction()
                            ?.replace(R.id.fragmentContainer, fragment)
                            ?.addToBackStack(null)
                            ?.commit()
                    }
                }
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