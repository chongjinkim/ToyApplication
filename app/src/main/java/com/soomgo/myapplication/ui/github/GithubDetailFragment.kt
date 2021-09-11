package com.soomgo.myapplication.ui.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.soomgo.myapplication.data.model.User
import com.soomgo.myapplication.databinding.FragmentGithubDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GithubDetailFragment : Fragment() {
    lateinit var binding: FragmentGithubDetailBinding
    val viewModel: GithubViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentGithubDetailBinding.inflate(
        inflater, container, false
    ).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = arguments?.getParcelable<User>(GITHUB_USER)

        binding.apply {
            user?.let { user ->
                Glide.with(this.root).load(user.avatar_url).into(detailImage)
                detailName.text = user.login
            }
        }
    }

    companion object {
        const val GITHUB_USER = "GITHUB_USER"

        @JvmStatic
        fun newInstance(user: User) = GithubDetailFragment().apply {
            arguments = Bundle().apply { putParcelable(GITHUB_USER, user) }


        }
    }
}