package com.umc.project.flo.ui.main.locker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.project.flo.data.local.SongDatabase
import com.umc.project.flo.databinding.FragmentLockerLikedAlbumBinding

class LockerLikedAlbumFragment: Fragment() {
    lateinit var binding: FragmentLockerLikedAlbumBinding
    private lateinit var albumDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerLikedAlbumBinding.inflate(inflater, container, false)
        albumDB = SongDatabase.getInstance(requireContext())!!
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        val adapter = LockerAlbumRVAdapter()
        binding.recyclerFragLockerAlbum.adapter = adapter
        binding.recyclerFragLockerAlbum.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter.addAlbums(albumDB.albumDao().getLikedAlbums(getJwt()) as ArrayList)

        Log.d("LockerLikedAlbum/initRecycler", albumDB.albumDao().getLikedAlbums(getJwt()).toString())
    }

    private fun getJwt(): Int{
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val jwt = spf!!.getInt("jwt", 0)
        return jwt
    }
}