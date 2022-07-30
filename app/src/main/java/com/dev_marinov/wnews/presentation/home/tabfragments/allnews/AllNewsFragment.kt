package com.dev_marinov.wnews.presentation.home.tabfragments.allnews

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dev_marinov.wnews.R
import com.dev_marinov.wnews.databinding.FragmentAllNewsBinding
import com.dev_marinov.wnews.presentation.home.HomeFragmentDirections
import com.google.android.material.snackbar.Snackbar

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

import android.widget.FrameLayout

import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView


@AndroidEntryPoint
class AllNewsFragment : Fragment() {

    lateinit var binding: FragmentAllNewsBinding
    lateinit var viewModel: AllNewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return initInterFace(inflater = inflater, container = container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNavigation()
    }

    private fun initInterFace(inflater: LayoutInflater, container: ViewGroup?): View {
        container?.let { container.removeAllViewsInLayout() }

        val orientation = requireActivity().resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_news, container, false)
            setLayout(1)
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_news, container, false)
            setLayout(2)
        }

        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun setLayout(column: Int){

        viewModel = ViewModelProvider(this)[AllNewsViewModel::class.java]

        val homeAdapter = AllNewsAdapter(viewModel::onClick, viewModel::onClickFavorite)

        homeAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL);

        binding.homeRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = staggeredGridLayoutManager
            adapter = homeAdapter
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.news.collectLatest {
                    homeAdapter.submitList(it)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.swipe.collect {
                    binding.swipeContainer.isRefreshing = it
                }
            }
        }

        binding.swipeContainer.setOnRefreshListener {
            viewModel.onSwipe()
        }
    }

    private fun setUpNavigation(){
        viewModel.uploadData.observe(viewLifecycleOwner, Observer {
            navigateToWebViewFragment(it)
        })
    }

    private fun navigateToWebViewFragment(url: String) {
        val action = HomeFragmentDirections.actionViewPager2FragmentToAllNewsWebViewFragment(url = url)
        findNavController().navigate(action)
    }

}