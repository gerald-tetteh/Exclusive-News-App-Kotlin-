package com.addodevelop.exclusivenews.news_detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.addodevelop.exclusivenews.R
import com.addodevelop.exclusivenews.databinding.NewsDetailFragmentBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class NewsDetailFragment : Fragment() {

    companion object {
        fun newInstance() = NewsDetailFragment()
    }

    private lateinit var viewModel: NewsDetailViewModel
    private lateinit var binding: NewsDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewsDetailFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val arguments: NewsDetailFragmentArgs by navArgs()
        val viewModelFactory = NewsDetailViewModelFactory(arguments.newsItem)
        viewModel = ViewModelProvider(this,viewModelFactory).get(NewsDetailViewModel::class.java)

        binding.viewModel = viewModel
        binding.newsItem = arguments.newsItem
        val animation = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
        val imageUri = arguments.newsItem.urlToImage?.toUri()?.buildUpon()?.scheme("https")?.build()
        postponeEnterTransition()
        Glide
            .with(requireContext())
            .load(imageUri)
            .listener(object: RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .apply {
                placeholder(R.drawable.ic_iconmonstr_globe_3)
                error(R.drawable.ic_baseline_broken_image)
            }
            .into(binding.newsImage)

        binding.viewArticleButton.setOnClickListener {
            arguments.newsItem.url?.let {
                findNavController().navigate(NewsDetailFragmentDirections.actionNewsDetailFragmentToWebViewFragment(it))
            }
        }
        return binding.root
    }



}