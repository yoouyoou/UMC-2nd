package com.umc.project.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.project.flo.databinding.FragmentLockerBinding

class LockerFragment : Fragment() {
    lateinit var binding: FragmentLockerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        //뷰페이저에 어댑터 연결
        val adapter = LockerVPAdapter(this)
        binding.vpFragLocker.adapter = adapter
        binding.vpFragLocker.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //탭레이아웃과 뷰페이저 연결
        val menu = arrayListOf("저장한 곡", "음악파일")
        TabLayoutMediator(binding.tlFragLockerMenu, binding.vpFragLocker){
            tab, position ->
            tab.text = menu[position]
        }.attach()
        return binding.root
    }
}