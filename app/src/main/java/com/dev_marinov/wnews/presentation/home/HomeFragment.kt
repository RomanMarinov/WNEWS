package com.dev_marinov.wnews.presentation.home

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.dev_marinov.wnews.R
import com.dev_marinov.wnews.databinding.FragmentHomeBinding
import com.dev_marinov.wnews.presentation.home.tabfragments.business.BusinessFragment
import com.dev_marinov.wnews.presentation.home.tabfragments.entertainment.EntertainmentFragment
import com.dev_marinov.wnews.presentation.home.tabfragments.health.HealthFragment
import com.dev_marinov.wnews.presentation.home.tabfragments.allnews.AllNewsFragment
import com.dev_marinov.wnews.presentation.home.tabfragments.science.ScienceFragment
import com.dev_marinov.wnews.presentation.home.tabfragments.sport.SportFragment
import com.dev_marinov.wnews.presentation.home.tabfragments.technology.TechnologyFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel

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
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        }
        setViewPager2Adapter()

        return binding.root
    }

    private fun setViewPager2Adapter() {

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val adapterViewPager2 = HomeAdapter(requireActivity(), createListFragments())
        adapterViewPager2.setData(createListFragments())
        binding.viewPager2.adapter = adapterViewPager2

        // тут надо установить наблюдатель для viewPager2ViewModel.lastTab
//            viewPager2ViewModel.lastTab.


        viewModel.lastTab.observe(viewLifecycleOwner){
            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(it))
            binding.viewPager2.currentItem = it
        }


//        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(viewModel.lastTab))
//        binding.viewPager2.currentItem = viewModel.lastTab

        setTabLayoutMediator(addHeadlinesTab())
        setViewPager2()
    }

    // устанавливаем заголовки для табов
    private fun addHeadlinesTab(): ArrayList<String> {
        val headlinesTab: ArrayList<String> = ArrayList()
        headlinesTab.add(resources.getString(R.string.main))
        headlinesTab.add(resources.getString(R.string.sport))
        headlinesTab.add(resources.getString(R.string.business))
        headlinesTab.add(resources.getString(R.string.entertainment))
        headlinesTab.add(resources.getString(R.string.health))
        headlinesTab.add(resources.getString(R.string.science))
        headlinesTab.add(resources.getString(R.string.technology))
        return headlinesTab
    }

    private fun createListFragments(): ArrayList<Fragment> {
        val fragmentList: ArrayList<Fragment> = ArrayList()
        fragmentList.add(AllNewsFragment())
        fragmentList.add(SportFragment())
        fragmentList.add(BusinessFragment())
        fragmentList.add(EntertainmentFragment())
        fragmentList.add(HealthFragment())
        fragmentList.add(ScienceFragment())
        fragmentList.add(TechnologyFragment())
        return fragmentList
    }

    // TabLayoutMediator для синхронизации компонента TabLayout с ViewPager2
    // установка текста заголовков вкладок, стиля вкладок
    private fun setTabLayoutMediator(titleTab: ArrayList<String>) {
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = titleTab[position]
        }.attach()
    }

    private fun setViewPager2() {
        binding.viewPager2.offscreenPageLimit = 3

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                viewModel.onSelectTabPosition(position)

                //viewModel.lastTab = position
                Log.e("333", " onPageSelected position$position")
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                //tabLayout.selectTab(tabLayout.getTabAt(viewModelTabViewPager2.lastTab))
                super.onPageScrollStateChanged(state)
            }
        })
    }
}