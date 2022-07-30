package com.dev_marinov.wnews.presentation.favorites

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dev_marinov.wnews.R
import com.dev_marinov.wnews.databinding.FragmentFavoritesBinding
import com.dev_marinov.wnews.presentation.adapter.FavoritesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    lateinit var binding: FragmentFavoritesBinding
    private val viewModel by viewModels<FavoritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return initInterFace(inflater, container)
    }

    private fun initInterFace(inflater: LayoutInflater, container: ViewGroup?): View {
        container?.let { container.removeAllViewsInLayout() }

        val orientation = requireActivity().resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
            setLayout(1)
        } else {
            binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
            setLayout(2)
        }
        return binding.root
    }

    private fun setLayout(column: Int) {
        val adapter = FavoritesAdapter(viewModel::onClick, viewModel::onClickFavorite)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL);

        binding.recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = staggeredGridLayoutManager
            this.adapter = adapter
        }

        viewModel.news.asLiveData().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

}