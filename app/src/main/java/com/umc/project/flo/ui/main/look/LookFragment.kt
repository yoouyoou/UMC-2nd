package com.umc.project.flo.ui.main.look

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.project.flo.data.remote.SongService
import com.umc.project.flo.data.remote.FloChartResult
import com.umc.project.flo.databinding.FragmentLookBinding

class LookFragment : Fragment(), LookView {

    private lateinit var binding : FragmentLookBinding
    private lateinit var floChartAdapter: FloChartRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getSongs()
    }

    private fun getSongs(){
        val songService = SongService()
        songService.setLookView(this)
        songService.getSongs()
    }

    private fun initRecyclerView(result: FloChartResult){
        floChartAdapter = FloChartRVAdapter(requireContext(), result)
        binding.lookFloChartRv.adapter = floChartAdapter
    }

    override fun onGetSongLoading() {
        binding.lookLoadingPb.visibility = View.VISIBLE
    }

    override fun onGetSongSuccess(code: Int, result: FloChartResult) {
        //성공시 로딩창 없애고 리사이클러뷰 처리
        binding.lookLoadingPb.visibility = View.GONE
        initRecyclerView(result)
    }

    override fun onGetSongFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }
}