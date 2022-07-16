package com.dev_marinov.wnews.presentation.home.tabfragments.sport

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dev_marinov.wnews.R
import com.dev_marinov.wnews.databinding.FragmentAllNewsBinding
import com.dev_marinov.wnews.databinding.FragmentSportBinding
import com.dev_marinov.wnews.presentation.home.HomeFragmentDirections
import com.dev_marinov.wnews.presentation.home.tabfragments.allnews.AllNewsAdapter

import com.dev_marinov.wnews.presentation.home.tabfragments.allnews.AllNewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportFragment : Fragment() {

    lateinit var binding: FragmentSportBinding
    lateinit var viewModel: SportViewModel

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
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sport, container, false)
            setLayout(1)
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sport, container, false)
            setLayout(2)
        }

        return binding.root
    }

    private fun setLayout(column: Int){
        viewModel = ViewModelProvider(this)[SportViewModel::class.java]

        val sportAdapter = SportAdapter(viewModel::onClick)

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL);

        binding.recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = staggeredGridLayoutManager
            adapter = sportAdapter
        }

        viewModel.sportNews.observe(viewLifecycleOwner){
            sportAdapter.refreshNews(it)
            binding.swipeContainer.isRefreshing = true;
        }

        // ВОЗМОЖНО НЕ ПРАВИЛЬНО ЧТО ОБРАЩАЮСЬ К viewModel
        binding.swipeContainer.setOnRefreshListener {
            binding.swipeContainer.isRefreshing = true;
            viewModel.getCategoryNews()
        }
    }

    private fun setUpNavigation(){
        viewModel.uploadData.observe(viewLifecycleOwner) {
            navigateToWebViewFragment(it)
        }
    }

    private fun navigateToWebViewFragment(url: String) {
        val action = HomeFragmentDirections.actionViewPager2FragmentToSportWebViewFragment("https://google.com")
        findNavController().navigate(action)
    }
}