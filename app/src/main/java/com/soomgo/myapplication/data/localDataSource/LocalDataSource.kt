package com.soomgo.myapplication.data.localDataSource

import androidx.recyclerview.widget.DiffUtil
import com.soomgo.myapplication.R

class LocalDataSource {
    val mainList = mutableListOf<MainList>().apply {
        repeat(100){
            add(MainList(title = "Title $it", subTitle = "SubTitle $it" ))
        }
    }
}

data class MainList(
    val image: Int = R.drawable.ic_launcher_foreground,
    val title: String = "Title",
    val subTitle: String = "SubTitle"
){
    companion object{
        val DiffUtil = object : DiffUtil.ItemCallback<MainList>(){
            override fun areItemsTheSame(oldItem: MainList, newItem: MainList): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MainList, newItem: MainList): Boolean {
                return oldItem.title == newItem.title
            }

        }
    }
}