package com.dev_marinov.wnews.presentation.home.tabfragments.entertainment

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dev_marinov.wnews.R
import com.dev_marinov.wnews.databinding.FragmentEntertainmentBinding
import com.dev_marinov.wnews.presentation.adapter.NewsAdapter
import com.dev_marinov.wnews.presentation.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EntertainmentFragment : Fragment() {

    lateinit var binding: FragmentEntertainmentBinding
    val viewModel by viewModels<EntertainmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return initInterFace(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNavigation()
    }

    private fun initInterFace(inflater: LayoutInflater, container: ViewGroup?): View {
        container?.let { container.removeAllViewsInLayout() }
        val orientation = requireActivity().resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_entertainment, container, false)
            setLayout(1)
        } else {
            binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_entertainment, container, false)
            setLayout(2)
        }
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun setLayout(column: Int) {
        val adapter = NewsAdapter(viewModel::onClick, viewModel::onClickFavorite)
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL);

        binding.recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = staggeredGridLayoutManager
            this.adapter = adapter
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.news.collectLatest {
                    adapter.submitList(it)
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

    private fun setUpNavigation() {
        viewModel.uploadData.observe(viewLifecycleOwner) {
            navigateToWebViewFragment(it)
        }
    }

    private fun navigateToWebViewFragment(url: String) {
        val action =
            HomeFragmentDirections.actionViewPager2FragmentToEntertainmentWebViewFragment(url)
        findNavController().navigate(action)
    }
}