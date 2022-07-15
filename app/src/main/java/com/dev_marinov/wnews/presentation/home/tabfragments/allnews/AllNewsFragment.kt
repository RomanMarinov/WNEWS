package com.dev_marinov.wnews.presentation.home.tabfragments.allnews

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

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllNewsFragment : Fragment() {

    lateinit var binding: FragmentAllNewsBinding
    lateinit var viewModel: AllNewsViewModel

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
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_news, container, false)
            setLayout(1)
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_news, container, false)
            setLayout(2)
        }

        return binding.root
    }

    private fun setLayout(column: Int){

        viewModel = ViewModelProvider(this)[AllNewsViewModel::class.java]

        val homeAdapter = AllNewsAdapter(viewModel::onClick)

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL);

        binding.homeRecyclerView.apply {
            setHasFixedSize(false)
            layoutManager = staggeredGridLayoutManager
            adapter = homeAdapter
        }

        viewModel.homeNews.observe(viewLifecycleOwner){
            homeAdapter.refreshNews(it)
        }



//        ////        adapterListHome.notifyDataSetChanged();
//        Handler().postDelayed({
//            try {
//                activity!!.runOnUiThread {
//                    staggeredGridLayoutManager.scrollToPositionWithOffset(
//                        lastVisableItem,
//                        0
//                    )
//                }
//            } catch (e: Exception) {
//                Log.e("444", "-try catch FragmentHome 1-$e")
//            }
//        }, 500)
    }

    private fun setUpNavigation(){
        viewModel.uploadData.observe(viewLifecycleOwner) {
            navigateToWebViewFragment(it)
        }
    }

    private fun navigateToWebViewFragment(url: String) {
        val action = AllNewsFragmentDirections.actionAllNewsFragmentToAllNewsWebViewFragment("https://yandex.ru")
        findNavController().navigate(action)
    }
}