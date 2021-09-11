package com.soomgo.myapplication.di

import com.google.gson.GsonBuilder
import com.soomgo.myapplication.MainViewModel
import com.soomgo.myapplication.data.network.Client
import com.soomgo.myapplication.data.remoteDataSource.CoroutineGithubRepository
import com.soomgo.myapplication.data.remoteDataSource.CoroutineGithubRepositoryImpl
import com.soomgo.myapplication.data.remoteDataSource.GithubRepository
import com.soomgo.myapplication.data.remoteDataSource.GithubRepositoryImpl
import com.soomgo.myapplication.ui.github.CoroutineGithubListViewModel
import com.soomgo.myapplication.ui.github.GithubViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val utilModule = module {
    single { GsonBuilder().setPrettyPrinting().create() }
}

val repositoryModule = module {
    single { Client(get()) }
    single<GithubRepository> { GithubRepositoryImpl(get()) }
    single<CoroutineGithubRepository> { CoroutineGithubRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { GithubViewModel(get()) }
    viewModel { CoroutineGithubListViewModel(get()) }
}


val applicationModule = listOf(
    utilModule,
    repositoryModule,
    viewModelModule,
//    localDataModule,
//    commonModule
)
