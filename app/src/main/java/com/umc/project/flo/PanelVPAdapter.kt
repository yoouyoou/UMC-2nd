package com.umc.project.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PanelVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragList: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int = fragList.size

    override fun createFragment(position: Int): Fragment {
        return fragList[position]
    }

    fun addFragment(fragment: Fragment){
        fragList.add(fragment)
        notifyItemInserted(fragList.size - 1)
    }

}