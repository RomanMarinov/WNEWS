package com.dev_marinov.wnews.presentation.home.tabfragments.allnews

import android.content.res.Resources
import android.content.res.Resources.getSystem
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import android.view.Gravity
import com.dev_marinov.wnews.R
import com.dev_marinov.wnews.databinding.ItemNewsBinding
import com.dev_marinov.wnews.presentation.model.SelectableFavoriteNews

class AllNewsAdapter(
        private val onClick: (url: String) -> Unit,
    private val onClickFavorite: (news: SelectableFavoriteNews) -> Unit
) : ListAdapter<SelectableFavoriteNews, AllNewsAdapter.ViewHolder>(AllNewsDiffUtilCallback()) {

    private var news: List<SelectableFavoriteNews> = ArrayList()

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

    override fun submitList(list: List<SelectableFavoriteNews>?) {
        super.submitList(list)
        list?.let { this.news = it.toList() }
    }

    inner class ViewHolder( // inner вложенный класс, который может обращаться к компонентам внешнего класса
        private val binding: ItemNewsBinding,
        private val onClick: (url: String) -> Unit,
        private val onClickFavorite: (news: SelectableFavoriteNews) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(news: SelectableFavoriteNews) {
            binding.itemNews = news.news

            val width = getSystem().displayMetrics.widthPixels

            news.news.urlToImage?.let {
                Picasso.get()
                    .load(news.news.urlToImage).memoryPolicy(MemoryPolicy.NO_CACHE)
                    .placeholder(R.drawable.picture_not_available)
                    .resize(width, 0).centerCrop(Gravity.TOP)
                    .into(binding.img)
            }

            // написать условие для установки красного избранное
            setBackgroundFavorite(news.isSelected)

            binding.cardView.setOnClickListener {
                onClick(news.news.url)
            }
            // клик для фаворита
            binding.llFavorite.setOnClickListener {
                onClickFavorite(news)
            }

           // binding.executePendingBindings()
        }


        private fun setBackgroundFavorite(isSelected: Boolean) {
            return if (isSelected) {
                binding.imgFavorite.setBackgroundResource(R.drawable.ic_favorite_on)
            } else {
                binding.imgFavorite.setBackgroundResource(R.drawable.ic_favorite_off)
            }
        }
    }

    class AllNewsDiffUtilCallback : DiffUtil.ItemCallback<SelectableFavoriteNews>() {
        override fun areItemsTheSame(
            oldItem: SelectableFavoriteNews,
            newItem: SelectableFavoriteNews
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: SelectableFavoriteNews,
            newItem: SelectableFavoriteNews
        ): Boolean {
            return oldItem.isSelected == newItem.isSelected
        }

    }
}

