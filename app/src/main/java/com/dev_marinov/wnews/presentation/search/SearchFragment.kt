package com.dev_marinov.wnews.presentation.search

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.TransitionManager
import com.dev_marinov.wnews.R
import com.dev_marinov.wnews.databinding.FragmentSearchBinding
import com.dev_marinov.wnews.presentation.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

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

        binding.recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = staggeredGridLayoutManager
            adapter = searchAdapter
        }

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
            searchAdapter.submitList(it)
        }
    }

    private fun setUpNavigation(){
        viewModel.uploadData.observe(viewLifecycleOwner) {
            navigateToWebViewFragment(it)
        }
    }

    private fun navigateToWebViewFragment(url: String) {
        val action = SearchFragmentDirections.actionSearchFragmentToSearchWebViewFragment(url)
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