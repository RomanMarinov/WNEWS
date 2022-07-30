package com.dev_marinov.wnews.presentation.favorites

import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev_marinov.wnews.R
import com.dev_marinov.wnews.databinding.ItemNewsFavoriteBinding
import com.dev_marinov.wnews.domain.News
import com.dev_marinov.wnews.presentation.home.tabfragments.business.BusinessAdapter
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

class FavoritesAdapter(
    private val onClick: (url: String) -> Unit,
    private val onClickFavorite: (news: News) -> Unit,
) : ListAdapter<News, FavoritesAdapter.ViewHolder>(AllNewsDiffUtilCallback()) {

    private var news: List<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemNewsFavoriteBinding.inflate(inflater, parent, false)
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

    override fun submitList(list: List<News>?) {
        super.submitList(list)
        list?.let { this.news = it.toList() }
    }

//    //передаем данные и оповещаем адаптер о необходимости обновления списка
//    fun refreshNews(list: List<News>) {
//        this.news = list
//        notifyDataSetChanged()
//    }

    inner class ViewHolder( // inner вложенный класс, который может обращаться к компонентам внешнего класса
        private val binding: ItemNewsFavoriteBinding,
        private val onClick: (url: String) -> Unit,
        private val onClickFavorite: (news: News) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News) {
            binding.itemNewsFavorite = news

            val width = Resources.getSystem().displayMetrics.widthPixels

            news.urlToImage?.let {
                Picasso.get()
                    .load(news.urlToImage).memoryPolicy(MemoryPolicy.NO_CACHE)
                    .placeholder(R.drawable.picture_not_available)
                    .resize(width, 0).centerCrop(Gravity.TOP)
                    .into(binding.img)
            }

            // написать условие для установки красного избранное
            //setBackgroundFavorite(news.isSelected)

            binding.cardView.setOnClickListener {
                onClick(news.url)
            }

            // клик для фаворита
            binding.llFavorite.setOnClickListener {
                // удалить новость

                onClickFavorite(news)
            }
        }

//        private fun setBackgroundFavorite(isSelected: Boolean) {
//                return if (isSelected) {
//                    binding.imgFavorite.setBackgroundResource(R.drawable.ic_favorite_on)
//                } else {
//                    binding.imgFavorite.setBackgroundResource(R.drawable.ic_favorite_off)
//                }
//            }
    }

    class AllNewsDiffUtilCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(
            oldItem: News,
            newItem: News
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: News,
            newItem: News
        ): Boolean {
            return oldItem == newItem
        }

    }

}