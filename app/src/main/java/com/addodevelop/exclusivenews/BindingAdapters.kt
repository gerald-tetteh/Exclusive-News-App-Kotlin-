package com.addodevelop.exclusivenews

import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.addodevelop.exclusivenews.countries.Country
import com.addodevelop.exclusivenews.network.NetWorkStatus
import com.addodevelop.exclusivenews.network.NewsItem
import com.addodevelop.exclusivenews.saved.SavedAdapter
import com.addodevelop.exclusivenews.saved.SavedClickListener
import com.addodevelop.exclusivenews.top_stories.TopStoriesAdapter
import com.addodevelop.exclusivenews.top_stories.TopStoriesClickListener
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import kotlin.random.Random

private val badgeColors = listOf(
    R.color.amber,
    R.color.blue_grey_darken_4,
    R.color.teal_200,
    R.color.purple_700,
    R.color.black,
    android.R.color.holo_purple,
    android.R.color.holo_red_dark,
    android.R.color.holo_green_dark,
    android.R.color.holo_orange_dark
)

private fun checkString(context: Context, text: String?): String {
    return when {
        text == null -> {
            context.getString(R.string.unknown)
        }
        text.isEmpty() -> {
            context.getString(R.string.unknown)
        }
        else -> {
            text
        }
    }
}

private fun pickBgColor(): Int {
    val index = Random.nextInt(0,9)
    return badgeColors[index]
}

// Binding Adapters

//Loads Country flag image into image view with Glide
@BindingAdapter("setCountryImage")
fun bindCountryImage(imageView: ImageView,country: Country) {
    val imageUrl = "https://www.countryflags.io/${country.iso}/shiny/64.png"
    val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
    Glide
        .with(imageView.context)
        .load(imageUri)
        .apply {
            placeholder(R.drawable.ic_iconmonstr_globe_3)
            error(R.drawable.ic_baseline_broken_image)
        }
        .into(imageView)
}

//Retrieves Country name from string resource an applies it to the text
//view
@BindingAdapter("setCountryName")
fun bindCountryName(textView: TextView,country: Country) {
    val countryName = textView.context.resources.getString(country.name)
    textView.text = countryName
}

//Sets news image
@BindingAdapter("setNewsImage")
fun bindNewsImage(imageView: ImageView, newsItem: NewsItem?) {
    val imageUri = newsItem?.urlToImage?.toUri()?.buildUpon()?.scheme("https")?.build()
    Glide
        .with(imageView.context)
        .load(imageUri)
        .apply {
            placeholder(R.drawable.ic_iconmonstr_globe_3)
            error(R.drawable.ic_baseline_broken_image)
        }
        .into(imageView)
}

//sets recyclerview list items
@BindingAdapter("setListItems")
fun bindNewsListItems(recyclerView: RecyclerView,newsItems: List<NewsItem>?) {
    val adapter = recyclerView.adapter as TopStoriesAdapter
    val orientation = recyclerView.context.resources.configuration.orientation
    if (newsItems != null) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            adapter.addNewsItemsWithHeader(newsItems)
        } else {
            adapter.addNewsItemsWithoutHeader(newsItems)
        }
    }
}

//sets banner text
@BindingAdapter("setPublisherBanner")
fun bindPublisherBanner(textView: TextView, newsItem: NewsItem?) {
    textView.text = checkString(textView.context,newsItem?.source?.name)
    textView.setBackgroundColor(ContextCompat.getColor(textView.context,pickBgColor()))
}

//sets news title
@BindingAdapter("setNewsTitle")
fun bindNewsTitle(textView: TextView, newsItem: NewsItem?) {
    textView.text = checkString(textView.context,newsItem?.title)
}

//sets news content
@BindingAdapter("setNewsContent")
fun bindNewsContent(textView: TextView, newsItem: NewsItem?) {
    val text = checkString(textView.context,newsItem?.content)
    if (text == textView.context.getString(R.string.unknown)) {
        textView.text = textView.context.getString(R.string.details_unavailable)
    } else {
        if (text.contains("[+")) {
            val index = text.indexOf("[+")
            textView.text = text.substring(0,index)
        } else {
            textView.text = text
        }
    }
}

//sets news author
@BindingAdapter("setNewsAuthor")
fun bindNewsAuthor(textView: TextView, newsItem: NewsItem?) {
    val text = checkString(textView.context,newsItem?.author)
    textView.text = if (text == textView.context.getString(R.string.unknown))
        textView.context.getString(R.string.unknown_author) else text
}

// sets network icon visibility
@BindingAdapter("setNetworkIconVisibility")
fun bindNetworkIconVisibility(imageView: ImageView, netWorkStatus: NetWorkStatus?) {
    when (netWorkStatus) {
        NetWorkStatus.DONE -> {
            imageView.visibility = View.GONE
        }
        NetWorkStatus.ERROR -> {
            imageView.setImageResource(R.drawable.ic_no_wifi)
            imageView.visibility = View.VISIBLE
        }
        NetWorkStatus.LOADING -> {
            imageView.visibility = View.GONE
        }
    }
}

// sets progress indicator visibility
@BindingAdapter("setProgressIndicatorVisibility")
fun bindProgressIndicatorVisibility(progressBar: ProgressBar, netWorkStatus: NetWorkStatus?) {
    when (netWorkStatus) {
        NetWorkStatus.DONE -> {
            progressBar.visibility = View.GONE
        }
        NetWorkStatus.ERROR -> {
            progressBar.visibility = View.GONE
        }
        NetWorkStatus.LOADING -> {
            progressBar.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter(value = ["setOnClickListener","setNewsItem"])
fun bindOnClickListener(imageView: ImageView, topStoriesClickListener: TopStoriesClickListener, newsItem: NewsItem?) {
    newsItem?.let {
        imageView.setOnClickListener {
            topStoriesClickListener.onClick(it, newsItem)
        }
    }
}

@BindingAdapter("setSavedListItems")
fun bindSavedListItems(recyclerView: RecyclerView,newsItems: List<NewsItem>?) {
    val adapter = recyclerView.adapter as SavedAdapter
    adapter.addItems(newsItems)
}

@BindingAdapter(value = ["setSavedOnClick","setSavedNewsItem"])
fun bindSavedClickListener(cardView: MaterialCardView, savedClickListener: SavedClickListener, newsItem: NewsItem?) {
    newsItem?.let {
        cardView.setOnClickListener { card ->
            savedClickListener.onClick(card, it)
        }
    }
}

@BindingAdapter("setSavedProgress")
fun binsSavedProgress(progressBar: ProgressBar, status: Boolean) {
    when (status) {
        true -> progressBar.visibility = View.GONE
        false -> progressBar.visibility = View.VISIBLE
    }
}