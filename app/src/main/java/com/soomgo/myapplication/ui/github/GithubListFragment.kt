package com.soomgo.myapplication.ui.github

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ListAdapter
import com.soomgo.myapplication.data.model.User
import com.soomgo.myapplication.data.model.UserResponse
import com.soomgo.myapplication.databinding.FragmentMainBinding
import com.soomgo.myapplication.databinding.LayoutMainListBinding
import com.soomgo.myapplication.ui.fragment.MainFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubListFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    val githubAdapter = GithubAdapter()
    val viewModel: GithubViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentMainBinding.inflate(
        inflater, container, false
    ).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerview.apply {
            adapter = githubAdapter
        }

        viewModel.getUsers("aa").enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                githubAdapter.submitList(response.body()?.items)
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("GithubListFragment", "${t.message}")
            }

        })
    }

    //LIST ADAPTER
    class GithubAdapter : ListAdapter<User, MainFragment.MainViewHolder>(User.DiffUtil) {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MainFragment.MainViewHolder {
            val view =
                LayoutMainListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MainFragment.MainViewHolder(view)
        }

        override fun onBindViewHolder(holder: MainFragment.MainViewHolder, position: Int) {
            holder.binding.apply {
                title.text = getItem(position).login
                subTitle.text = getItem(position).repos_url
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String = "", param2: String = "") = GithubListFragment()
    }
}