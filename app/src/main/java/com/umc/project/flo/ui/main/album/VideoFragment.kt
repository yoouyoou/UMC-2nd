package com.umc.project.flo.ui.main.album

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.project.flo.databinding.FragmentVideoBinding

class VideoFragment : Fragment() {

    lateinit var binding: FragmentVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater, container, false)

        binding.tvVideoFragSorting.setOnClickListener{
            var builder = AlertDialog.Builder(this.context)
            builder.setTitle("정렬 순서 변경")
            builder.setMessage("Test")
            builder.create().window?.setGravity(Gravity.BOTTOM)
            //activity.window.setGravity(Gravity.BOTTOM)
            //builder.
            //getWindow().setGravity(Gravity.BOTTOM)
            builder.show()

        }
        return binding.root
    }
}