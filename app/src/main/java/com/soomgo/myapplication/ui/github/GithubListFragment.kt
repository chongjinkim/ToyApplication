package com.soomgo.myapplication.ui.github

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.soomgo.myapplication.R
import com.soomgo.myapplication.data.model.User
import com.soomgo.myapplication.data.model.UserResponse
import com.soomgo.myapplication.data.network.Client
import com.soomgo.myapplication.data.remoteDataSource.GithubRepositoryImpl
import com.soomgo.myapplication.databinding.FragmentMainBinding
import com.soomgo.myapplication.databinding.LayoutMainListBinding
import com.soomgo.myapplication.ui.fragment.MainFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
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
    ): View = FragmentMainBinding.inflate(
        inflater, container, false
    ).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerview.apply {
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
        /**
         * 문제점
         *  MVVM 구조에 맞지 않는다
         *      v : 레이아웃
         *      vm : 로직 / 상
         *  가독성 : 구조에 맞지 않는다
         *  번거로움 : 로직 성공/실패 -> viewModel
         * */

        viewModel.getUsers("kakao").enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                githubAdapter.submitList(response.body()?.items)
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("GithubListFragment", "${t.message}")
            }
        })
    }

    companion object {
        const val SELECTED_USER = "SELECTED_USER"
        const val USER_RESULT = "USER_RESULT"

        @JvmStatic
        fun newInstance() = GithubListFragment()
    }
}

//LIST ADAPTER
class GithubAdapter : ListAdapter<User, MainFragment.MainViewHolder>(User.DiffUtil), KoinComponent {
    val context : Context by inject()
    var clickListener: ((User) -> Unit)? = null
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
//            val stringRes = context.getString(R.string.app_name)
            Glide.with(this.root).load(getItem(position).avatar_url).into(image)
            title.text = getItem(position).login
            subTitle.text = getItem(position).repos_url
        }.also {
            it.root.setOnClickListener {
                clickListener?.invoke(getItem(position))
            }
        }
    }
}