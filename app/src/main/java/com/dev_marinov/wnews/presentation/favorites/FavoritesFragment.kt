package com.dev_marinov.wnews.presentation.favorites

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dev_marinov.wnews.R
import com.dev_marinov.wnews.databinding.FragmentBusinessBinding
import com.dev_marinov.wnews.databinding.FragmentFavoritesBinding
import com.dev_marinov.wnews.domain.News
import com.dev_marinov.wnews.presentation.home.HomeFragmentDirections
import com.dev_marinov.wnews.presentation.home.tabfragments.business.BusinessAdapter
import com.dev_marinov.wnews.presentation.home.tabfragments.business.BusinessViewModel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setUpNavigation()
    }

    private fun initInterFace(inflater: LayoutInflater, container: ViewGroup?): View {
        container?.let { container.removeAllViewsInLayout() }

        val orientation = requireActivity().resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
            setLayout(1)
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
            setLayout(2)
        }

        return binding.root
    }

    private fun setLayout(column: Int){

        val adapter = FavoritesAdapter(viewModel::onClick, viewModel::onClickFavorite)

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL);

        binding.recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = staggeredGridLayoutManager
            this.adapter = adapter
        }

        viewModel.news.asLiveData().observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

}








//    companion object {
//        val KEY_URL = "key_url"
//
//        fun createInstance(listFav: List<News>) : FavoritesFragment {
//            val fragment = FavoritesFragment()
//            val bundle = Bundle()
//            bundle.putStringArrayList(KEY_URL, listFav)
//            fragment.arguments = bundle
//            return fragment
//        }
//    }


//    private fun setUpNavigation(){
//        viewModel.uploadData.observe(viewLifecycleOwner) {
//            navigateToWebViewFragment(it)
//        }
//    }
//
//    private fun navigateToWebViewFragment(url: String) {
//        val action = HomeFragmentDirections.actionViewPager2FragmentToSportWebViewFragment("https://yandex.ru")
//        findNavController().navigate(action)
//    }
}