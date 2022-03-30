package com.umc.project.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.project.flo.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding
    private val information = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        binding.ivAlbumBack.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()
        }

        val albumAdapter = AlbumVPAdapter(this)
        binding.vpAlbumContent.adapter = albumAdapter
        TabLayoutMediator(binding.tlAlbumTab, binding.vpAlbumContent){
            tab, position -> tab.text = information[position]
        }.attach()

        return binding.root
    }
}