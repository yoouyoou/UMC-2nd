package com.umc.project.flo.ui.main.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerVPAdapter(fragment:Fragment) : FragmentStateAdapter(fragment) {

    private val fragmentList: ArrayList<Fragment> = ArrayList()

    //이 어댑터와 연결될 뷰페이저에게 데이터를 몇 개 전달할 것인지 알려주는 함수
    override fun getItemCount(): Int = Int.MAX_VALUE //fragmentList.size

    //뷰페이저 해당 item(프래그먼트)를 생성해주는 함수
    //override fun createFragment(position: Int): Fragment = fragmentList[position]
    override fun createFragment(position: Int): Fragment{
        var index = position % fragmentList.size
        println(index)
        return fragmentList[index]
    }

    fun addFragment(list: List<Fragment>){
        println("addFragment함수 들어옴")
        //fragmentList.add(fragment)
        fragmentList.apply{
            clear()
            addAll(list)
        }
//        notifyItemInserted(fragmentList.size-1)
        notifyDataSetChanged()
    }
}