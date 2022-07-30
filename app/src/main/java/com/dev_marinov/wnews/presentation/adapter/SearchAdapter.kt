package com.dev_marinov.wnews.presentation.adapter

import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev_marinov.wnews.databinding.ItemNewsBinding
import com.dev_marinov.wnews.domain.News
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

class SearchAdapter(
    private val onClick: (url: String) -> Unit
) : ListAdapter<News, SearchAdapter.ViewHolder>(SearchDiffUtilCallback()) {

    private var news: List<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemNewsBinding.inflate(inflater, parent, false)
        return ViewHolder(itemBinding, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        news[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun submitList(list: List<News>?) {
        super.submitList(list)
        list?.let { this.news = it.toList() }
    }

    inner class ViewHolder( // inner вложенный класс, который может обращаться к компонентам внешнего класса
        private val binding: ItemNewsBinding,
        private val onClick: (url: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News) {
            binding.itemNews = news

            val width = Resources.getSystem().displayMetrics.widthPixels

            Picasso.get()
                .load(news.urlToImage).memoryPolicy(MemoryPolicy.NO_CACHE)
                .resize(width, 0).centerCrop(Gravity.TOP)
                .into(binding.img) // -----> картинка

            binding.cardView.setOnClickListener {
                onClick(news.url)
            }
        }
    }

    class SearchDiffUtilCallback : DiffUtil.ItemCallback<News>(){
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }

    }
}


