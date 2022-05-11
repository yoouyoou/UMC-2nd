package com.umc.project.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.project.flo.databinding.FragmentLockerListBinding

class LockerListFragment: Fragment() {
    lateinit var binding: FragmentLockerListBinding
    lateinit var songDB: SongDatabase
    private var songList= ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerListBinding.inflate(inflater, container, false)
        songDB = SongDatabase.getInstance(requireContext())!!

        //더미 데이터
//        songList.apply {
//            add(Album("LILAC", "아이유 (IU)", R.drawable.img_album_exp2))
//            add(Album("Butter", "방탄소년단", R.drawable.img_album_exp))
//            add(Album("Next Level", "aespa", R.drawable.img_album_exp3))
//            add(Album("Boy with Luv", "방탄소년단", R.drawable.img_album_exp4))
//            add(Album("BBoom BBoom", "모모랜드", R.drawable.img_album_exp5))
//            add(Album("Weekend", "태연 (TAEYEON)", R.drawable.img_album_exp6))
//            add(Album("LILAC", "아이유 (IU)", R.drawable.img_album_exp2))
//            add(Album("Butter", "방탄소년단", R.drawable.img_album_exp))
//            add(Album("Next Level", "aespa", R.drawable.img_album_exp3))
//            add(Album("Boy with Luv", "방탄소년단", R.drawable.img_album_exp4))
//            add(Album("BBoom BBoom", "모모랜드", R.drawable.img_album_exp5))
//            add(Album("Weekend", "태연 (TAEYEON)", R.drawable.img_album_exp6))
//        }
//        val lockerAdapter = LockerRVAdapter(songList)
//        binding.recyclerFragLockerList.adapter = lockerAdapter
//        binding.recyclerFragLockerList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        initRecyclerView()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        val lockerAdapter = LockerRVAdapter(songList)
        lockerAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
        lockerAdapter.setMyItemClickListener(object: LockerRVAdapter.MyItemClickListener{
            override fun onRemoveSong(songId: Int) {
                songDB.songDao().updateIsLikeById(false, songId)
            }
        })
        binding.recyclerFragLockerList.adapter = lockerAdapter
        binding.recyclerFragLockerList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}