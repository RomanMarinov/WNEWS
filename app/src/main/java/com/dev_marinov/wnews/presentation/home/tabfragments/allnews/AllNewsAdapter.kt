package com.dev_marinov.wnews.presentation.home.tabfragments.allnews

import android.content.res.Resources.getSystem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev_marinov.wnews.databinding.ItemNewsBinding
import com.dev_marinov.wnews.domain.News
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import android.view.Gravity

class AllNewsAdapter(
    private val onClick: (url: String) -> Unit,
    private val onClickFavorite: (news: News) -> Unit
) : RecyclerView.Adapter<AllNewsAdapter.ViewHolder>() {

    private var news: List<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemNewsBinding.inflate(inflater, parent, false)
        return ViewHolder(itemBinding, onClick, onClickFavorite)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        news[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }

    fun refreshNews(list: List<News>) {
        this.news = list
        notifyDataSetChanged()
    }

    inner class ViewHolder( // inner вложенный класс, который может обращаться к компонентам внешнего класса
        private val binding: ItemNewsBinding,
        private val onClick: (url: String) -> Unit,
        private val onClickFavorite: (news: News) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News) {
            binding.itemNews = news

            val width = getSystem().displayMetrics.widthPixels

            Picasso.get()
                .load(news.urlToImage).memoryPolicy(MemoryPolicy.NO_CACHE)
                .resize(width, 0).centerCrop(Gravity.TOP)
                .into(binding.img)

            binding.cardView.setOnClickListener {
                onClick(news.url.toString())
            }
            // клик для фаворита
            binding.imgFavorite.setOnClickListener {
                onClickFavorite(news)
            }
        }

    }

}