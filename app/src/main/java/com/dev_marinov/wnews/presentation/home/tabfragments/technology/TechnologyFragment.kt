package com.dev_marinov.wnews.presentation.home.tabfragments.technology

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dev_marinov.wnews.R
import com.dev_marinov.wnews.databinding.FragmentSportBinding
import com.dev_marinov.wnews.databinding.FragmentTechnologyBinding
import com.dev_marinov.wnews.presentation.home.HomeFragmentDirections
import com.dev_marinov.wnews.presentation.home.tabfragments.science.ScienceAdapter
import com.google.android.material.snackbar.Snackbar

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TechnologyFragment : Fragment() {

    lateinit var binding: FragmentTechnologyBinding
    lateinit var viewModel: TechnologyViewModel

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
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_technology, container, false)
            setLayout(1)
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_technology, container, false)
            setLayout(2)
        }

        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun setLayout(column: Int){
        viewModel = ViewModelProvider(this)[TechnologyViewModel::class.java]

        val adapter = ScienceAdapter(viewModel::onClick, viewModel::onClickFavorite)

        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL);

        binding.recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = staggeredGridLayoutManager
            this.adapter = adapter
        }

//        viewModel.event.observe(viewLifecycleOwner, Observer {
//            Log.e("333", "=it=$it")
//            showMessage(it.message)
//        })

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
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

    private fun setUpNavigation(){
        viewModel.uploadData.observe(viewLifecycleOwner) {
            navigateToWebViewFragment(it)
        }
    }

    private fun navigateToWebViewFragment(url: String) {
        val action = HomeFragmentDirections.actionViewPager2FragmentToTechnologyWebViewFragment(url)
        findNavController().navigate(action)
    }


//    private fun showMessage(message: String) {
//        val snackBar = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
//        val snackBarLayout = snackBar.view
//        val layoutParams = snackBarLayout.layoutParams as FrameLayout.LayoutParams
//        snackBarLayout.setPadding(0, 0, 0, 0)
//        layoutParams.setMargins(10, 0, 10, 120)
//        snackBarLayout.layoutParams = layoutParams
//        snackBarLayout.background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_edittext, null)
//        snackBar.setTextColor(Color.BLACK)
//        snackBar.show()
//    }


}