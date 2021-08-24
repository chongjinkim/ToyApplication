package com.soomgo.myapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.soomgo.myapplication.data.localDataSource.LocalDataSource
import com.soomgo.myapplication.data.localDataSource.MainList
import com.soomgo.myapplication.databinding.FragmentMainBinding
import com.soomgo.myapplication.databinding.LayoutMainListBinding

class MainFragment : Fragment() {
    val viewModel: SharedViewModel by activityViewModels()

    lateinit var binding: FragmentMainBinding
    private val mainAdapter = MainAdapter(LocalDataSource().mainList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentMainBinding.inflate(inflater, container, false).apply {
        lifecycleOwner = this@MainFragment
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerview.adapter = mainAdapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String = "", param2: String = "") = MainFragment().apply {

        }
    }
}

class MainAdapter(private val list : List<MainList>) : RecyclerView.Adapter<MainAdapter.MainViewHolder>(){

    class MainViewHolder(val binding : LayoutMainListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutMainListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        holder.binding.apply {
            title.text = list[position].title
            subTitle.text = list[position].subTitle
        }
    }

    override fun getItemCount(): Int = list.size
}