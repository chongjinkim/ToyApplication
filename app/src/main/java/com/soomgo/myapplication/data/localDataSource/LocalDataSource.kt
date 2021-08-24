package com.soomgo.myapplication.data.localDataSource

import com.soomgo.myapplication.R

class LocalDataSource {
    val mainList = listOf(
        MainList(title = "Title 1 ", subTitle = "SubTitle 1 "),
        MainList(title = "Title 2 ", subTitle = "SubTitle 2 "),
        MainList(title = "Title 3 ", subTitle = "SubTitle 3 "),
        MainList(title = "Title 4 ", subTitle = "SubTitle 4 "),
        MainList(title = "Title 5 ", subTitle = "SubTitle 5 "),
        MainList(title = "Title 6 ", subTitle = "SubTitle 6 "),
        MainList(title = "Title 7 ", subTitle = "SubTitle 7 "),
        MainList(title = "Title 8 ", subTitle = "SubTitle 8 "),
        MainList(title = "Title 9 ", subTitle = "SubTitle 9 "),
        MainList(title = "Title 10", subTitle = "SubTitle 10"),
        MainList(title = "Title 11", subTitle = "SubTitle 11"),
        MainList(title = "Title 12", subTitle = "SubTitle 12"),
        MainList(title = "Title 13", subTitle = "SubTitle 13"),
        MainList(title = "Title 14", subTitle = "SubTitle 14"),
        MainList(title = "Title 15", subTitle = "SubTitle 15"),
        MainList(title = "Title 16", subTitle = "SubTitle 16"),
        MainList(title = "Title 17", subTitle = "SubTitle 17"),
        MainList(title = "Title 18", subTitle = "SubTitle 18"),
        MainList(title = "Title 19", subTitle = "SubTitle 19"),
        MainList(title = "Title 20", subTitle = "SubTitle 20"),
        MainList(title = "Title 21", subTitle = "SubTitle 21"),
        MainList(title = "Title 22", subTitle = "SubTitle 22")
    )
}

data class MainList(
    val image: Int = R.drawable.ic_launcher_foreground,
    val title: String = "Title",
    val subTitle: String = "SubTitle"
)