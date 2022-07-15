package com.dev_marinov.wnews.presentation.home

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.ArrayList

class HomeAdapter(
    requireActivity: FragmentActivity,
    var fragmentList: ArrayList<Fragment>) : FragmentStateAdapter(requireActivity) {

//    var fragmentList: ArrayList<Fragment> = ArrayList()

    override fun createFragment(position: Int): Fragment {
        Log.e("333","=positionAdapter=" + position)
        return fragmentList[position]
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    fun setData(fragments: ArrayList<Fragment>) {
        fragmentList = fragments
    }
}