package com.dev_marinov.wnews.presentation.search

import android.content.res.Configuration
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Display
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.TransitionManager
import com.dev_marinov.wnews.R
import com.dev_marinov.wnews.databinding.FragmentSearchBinding
import com.dev_marinov.wnews.databinding.FragmentSportBinding
import com.dev_marinov.wnews.presentation.home.tabfragments.sport.SportAdapter
import com.dev_marinov.wnews.presentation.home.tabfragments.sport.SportViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class SearchFragment : Fragment() {

    // GET https://newsapi.org/v2/everything?q=bitcoin&apiKey=f725144c0220437d87363920fe7b20ba

    lateinit var binding: FragmentSearchBinding
    lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

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
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
            setLayout(1)
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
            setLayout(2)
        }

        return binding.root
    }

    private fun setLayout(column: Int) {

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        val searchAdapter = SearchAdapter(viewModel::onClick)

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL);


        // ??????
        //if (searchViewModel.arrayListSearch.size != 0) { layoutChange() }

        binding.recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = staggeredGridLayoutManager
            adapter = searchAdapter
        }


//        viewModel.searchNews.observe(viewLifecycleOwner){
//            searchAdapter.refreshNews(it)
//        }

        val display: Display = requireActivity().getWindowManager().getDefaultDisplay()
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y

        binding.imgFragmentSearch.setOnClickListener(View.OnClickListener {
            layoutChange()
            val stringSearch = binding.textInputEditText.text.toString()
            var q = ""
            for (i in stringSearch.indices) {
                if (!(stringSearch[i] == ' ' && stringSearch[i + 1] == ' ')) {
                    q = if (stringSearch[i] == ' ') {
                        q + stringSearch[i].toString().replace(' ', '+')
                    } else {
                        q + stringSearch[i]
                    }
                }
            }

            viewModel.getSearchNews(q)
        })

        viewModel.searchNews.observe(viewLifecycleOwner){
            searchAdapter.refreshNews(it)
        }
    }

    private fun setUpNavigation(){
        viewModel.uploadData.observe(viewLifecycleOwner) {
            navigateToWebViewFragment(it)
        }
    }

    private fun navigateToWebViewFragment(url: String) {
        val action = SearchFragmentDirections.actionSearchFragmentToSearchWebViewFragment("https://yandex.ru")
        findNavController().navigate(action)
    }

    private fun layoutChange() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.clFragSearch)
        changeConstraints(constraintSet)
        TransitionManager.beginDelayedTransition(binding.clFragSearch)
        constraintSet.applyTo(binding.clFragSearch)
    }

    private fun changeConstraints(set: ConstraintSet) {
        set.clear(R.id.textInputLayout, ConstraintSet.BOTTOM)
        set.clear(R.id.imgFragmentSearch, ConstraintSet.BOTTOM)
        set.connect(R.id.textInputLayout, ConstraintSet.TOP, R.id.llTitle, ConstraintSet.BOTTOM)
        set.connect(
            R.id.imgFragmentSearch,
            ConstraintSet.TOP,
            R.id.llTitle,
            ConstraintSet.BOTTOM,
            10
        )
    }

}