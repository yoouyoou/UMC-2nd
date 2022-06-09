package com.umc.project.flo.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.umc.project.flo.*
import com.umc.project.flo.data.entities.Album
import com.umc.project.flo.data.local.SongDatabase
import com.umc.project.flo.databinding.FragmentHomeBinding
import com.umc.project.flo.ui.main.MainActivity
import com.umc.project.flo.ui.main.album.AlbumFragment

class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()
    private lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        //앨범리스트 및 더미데이터 생성
        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums())

        /*오늘발매음악 리사이클러뷰 작업*/
        //리사이클러뷰에 어댑터 연결 및 레이아웃 매니저 설정
        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.recyclerHomeAlbum.adapter = albumRVAdapter
        binding.recyclerHomeAlbum.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener {
            override fun onItemClick(album: Album) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, AlbumFragment().apply{
                        arguments = Bundle().apply{
                            val gson = Gson()
                            val albumJson = gson.toJson(album)
                            putString("album", albumJson)
                        }
                    })
                    .commitAllowingStateLoss()
            }
        })

//        binding.linearSongAlbumContent.setOnClickListener{
//            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, AlbumFragment())
//                .commitAllowingStateLoss()
//        }

        //홈화면 판넬 뷰페이저 연결
        val panelAdapter = PanelVPAdapter(this)
        panelAdapter.addFragment(PanelFragment(R.drawable.img_first_album_default))
        panelAdapter.addFragment(PanelFragment(R.drawable.img_first_album_default2))
        panelAdapter.addFragment(PanelFragment(R.drawable.img_first_album_default3))
        panelAdapter.addFragment(PanelFragment(R.drawable.img_first_album_default4))
        panelAdapter.addFragment(PanelFragment(R.drawable.img_first_album_default5))
        binding.homePanelBgIv.adapter = panelAdapter
        binding.homePanelBgIv.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        //인디케이터 작업
        binding.indicatorPanel.setViewPager2(binding.homePanelBgIv)
        /* tablayout으로 인디케이터 만듬
        TabLayoutMediator(binding.tlIndicatorPanel, binding.homePanelBgIv){
            tab, position ->
            binding.homePanelBgIv.currentItem = tab.position
        }.attach()
        */


        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(arrayListOf(BannerFragment(R.drawable.img_home_viewpager_exp),
            BannerFragment(R.drawable.img_home_viewpager_exp2)))
//        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp));
//        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2));
        binding.vpHomeIssue.adapter = bannerAdapter
        binding.vpHomeIssue.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }
}