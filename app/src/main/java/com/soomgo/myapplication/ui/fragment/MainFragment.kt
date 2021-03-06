package com.soomgo.myapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.*
import com.soomgo.myapplication.R
import com.soomgo.myapplication.data.localDataSource.LocalDataSource
import com.soomgo.myapplication.data.localDataSource.MainList
import com.soomgo.myapplication.databinding.FragmentMainBinding
import com.soomgo.myapplication.databinding.LayoutMainListBinding
import com.soomgo.myapplication.databinding.LayoutTitleBinding
import com.soomgo.myapplication.databinding.LayoutType1Binding
import java.lang.IllegalArgumentException

class MainFragment : Fragment() {
    val viewModel: SharedViewModel by activityViewModels()
    lateinit var binding: FragmentMainBinding
    var clickListener: ((String) -> Int)? = null

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
            /**0. layout manager*/
            //todo better on xml
            /*recyclerview.layoutManager = LinearLayoutManager(this@MainFragment.context)
                .apply {
                    orientation = LinearLayoutManager.VERTICAL
//                    orientation = LinearLayoutManager.HORIZONTAL
                }*/
//            recyclerview.layoutManager = GridLayoutManager(this@MainFragment.context, 3)
//            recyclerview.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            /**1. default*/
            recyclerview.adapter = MainAdapter(LocalDataSource().mainList)
//            recyclerview.adapter = SampleAdpater(LocalDataSource().mainList)

            /**2. concat adapter*/
//            recyclerview.adapter = ConcatAdapter().apply {
//                addAdapter(TitleAdapter("?????? 1"))
//                addAdapter(MainAdapter(LocalDataSource().mainList))
//                addAdapter(TitleAdapter("?????? 2"))
//                addAdapter(MainAdapter(LocalDataSource().mainList))
//            }

            /**3. multi type adapter*/
//            recyclerview.adapter = MultiTypeAdapter(LocalDataSource().mainList)

            /**4. list adapter*/
//            recyclerview.adapter = MainListAdapter().apply {
//                submitList(LocalDataSource().mainList)
//            }
        }
    }

    class MainViewHolder(val binding: LayoutMainListBinding) : RecyclerView.ViewHolder(binding.root)
    class Type1ViewHolder(val binding: LayoutType1Binding) : RecyclerView.ViewHolder(binding.root)

    //MAIN ADAPTER
    inner class MainAdapter(private val list: List<MainList>) :
        RecyclerView.Adapter<MainViewHolder>() {

        //todo CODE ARRANGEMENT
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view =
                LayoutMainListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MainViewHolder(view)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            holder.binding.apply {
                title.text = list[position].title
                subTitle.text = list[position].subTitle
                root.setOnClickListener {
                    clickListener?.invoke(list[position].title)
                }
            }
        }

        override fun getItemCount(): Int = list.size
    }

    //TITLE ADPATER
    class TitleAdapter(private val value: String) :
        RecyclerView.Adapter<TitleAdapter.TitleViewHolder>() {
        class TitleViewHolder(val binding: LayoutTitleBinding) :
            RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
            val view =
                LayoutTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TitleViewHolder(view)
        }

        override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
            holder.binding.apply {
                title.text = value
            }
        }

        override fun getItemCount(): Int = 1
    }

    //LIST ADAPTER
    inner class MainListAdapter : ListAdapter<MainList, MainViewHolder>(MainList.DiffUtil) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view =
                LayoutMainListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MainViewHolder(view)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            holder.binding.apply {
                title.text = getItem(position).title
                subTitle.text = getItem(position).subTitle
            }
        }
    }

    //MULTI TYPE ADAPTER
    class MultiTypeAdapter(private val list: List<MainList>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                R.layout.layout_main_list -> {
                    val view =
                        LayoutMainListBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    MainViewHolder(view)
                }
                R.layout.layout_type_1 -> {
                    val view =
                        LayoutType1Binding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    Type1ViewHolder(view)
                }
                else -> throw IllegalArgumentException("")
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder.itemViewType) {
                R.layout.layout_main_list -> {
                    val viewHolder: MainViewHolder = holder as MainViewHolder
                    viewHolder.binding.apply {
                        title.text = list[position].title
                        subTitle.text = list[position].subTitle
                    }
                }
                else -> {
                    val viewHolder: Type1ViewHolder = holder as Type1ViewHolder
                    viewHolder.binding.apply {
                        title.text = list[position].title
                        subTitle.text = list[position].subTitle
                    }
                }
            }
        }

        override fun getItemCount() = list.size
        override fun getItemViewType(position: Int): Int = when (position % 2 == 0) {
            true -> {
                R.layout.layout_main_list
            }
            false -> {
                R.layout.layout_type_1
            }
        }
//        override fun getItemViewType(position: Int): Int = when (list[position].type) {
//            1 -> {
//                R.layout.layout_main_list
//            }
//            2 -> {
//                R.layout.layout_type_1
//            }
//            else -> {
//                R.layout.layout_type_1
//            }
//        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String = "", param2: String = "") = MainFragment()
    }

    class SampleAdapter(val list: List<MainList>) : RecyclerView.Adapter<MainViewHolder>() {
        override fun getItemCount() = list.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val binding: LayoutMainListBinding = LayoutMainListBinding
                .inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

            return MainViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val item: MainList = list[position]
            holder.binding.image.setImageResource(item.image)
            holder.binding.title.text = item.title
            holder.binding.subTitle.text = item.subTitle
        }
    }
}


/**
0. type of recyclerview layout
1. linear layout
2. grid layout
3. staggeredLayout

1. xml
- Recyclerview
<Recyclerview
witdh
height
...
...
/>

2.??????
- declare Recyclerview adapter
- declare viewHodler
//
- Adapter : Recycerview.Adpater<ViewHolder>(){

}

3 glueing ????????????????????? ???????????? ????????? ??????, ???????????? ??????
- 1. let recyclerview knows which type of layout
recycerview.layoutManager = LinearLayout......
- 2. set adapter to recyclerview
 *
 * */