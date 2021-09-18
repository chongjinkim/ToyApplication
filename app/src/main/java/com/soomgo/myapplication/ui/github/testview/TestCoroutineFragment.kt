package com.soomgo.myapplication.ui.github.testview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soomgo.myapplication.data.model.User
import com.soomgo.myapplication.databinding.FragmentCoroutineTestBinding
import com.soomgo.myapplication.databinding.LayoutType1Binding
import kotlinx.android.synthetic.main.fragment_coroutine_test.*
import kotlinx.coroutines.launch

class TestCoroutineFragment : Fragment() {

    lateinit var binding : FragmentCoroutineTestBinding
    val adapter =GithubAdapter()
    val viewModel : TestCoroutineViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentCoroutineTestBinding.inflate(inflater, container, false).apply{
        lifecycleOwner = this@TestCoroutineFragment.viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview.adapter = adapter
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.getUser("kakao")
        }
        viewModel.user.observe(viewLifecycleOwner){
            adapter.setUsers(it)
        }
    }

    class GithubViewHolder(var binding: LayoutType1Binding) : RecyclerView.ViewHolder(binding.root) {

    }

    class GithubAdapter() : RecyclerView.Adapter<GithubViewHolder>() {

        var list = mutableListOf<User>()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubViewHolder {

            var binding = LayoutType1Binding.inflate(LayoutInflater.from(parent.context), parent, false)

            return GithubViewHolder(binding)
        }

        override fun onBindViewHolder(holder: GithubViewHolder, position: Int){
            holder.binding.title.text = list[position].login
            holder.binding.subTitle.text = list[position].html_url
            Glide.with(holder.itemView).load(list[position].avatar_url).into( holder.binding.image)

        }


        override fun getItemCount(): Int {
            return list.size
        }

        fun setUsers(user : List<User>){
            list= user.toMutableList()
        }
    }


}