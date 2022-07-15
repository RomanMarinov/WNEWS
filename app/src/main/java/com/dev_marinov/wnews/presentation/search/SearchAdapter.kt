package com.dev_marinov.wnews.presentation.search

import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev_marinov.wnews.databinding.ItemNewsBinding
import com.dev_marinov.wnews.domain.News
import com.dev_marinov.wnews.presentation.home.tabfragments.allnews.AllNewsAdapter
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso


class SearchAdapter(
    private val onClick: (url: String) -> Unit
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

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

    //передаем данные и оповещаем адаптер о необходимости обновления списка
    fun refreshNews(list: List<News>) {
        this.news = list
        notifyDataSetChanged()
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
                onClick(news.url.toString())
            }
        }

    }

}