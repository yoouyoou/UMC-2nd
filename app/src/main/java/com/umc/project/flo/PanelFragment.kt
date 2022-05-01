package com.umc.project.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.project.flo.databinding.FragmentPanelBinding

class PanelFragment(private val imgRes: Int): Fragment() {

    lateinit var binding : FragmentPanelBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPanelBinding.inflate(inflater, container, false)
        binding.ivPanel.setImageResource(imgRes)
        return binding.root
    }

}