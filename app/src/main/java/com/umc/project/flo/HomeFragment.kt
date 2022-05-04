package com.umc.project.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.umc.project.flo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        /*오늘발매음악 리사이클러뷰 작업*/
        //더미 데이터
        albumDatas.apply {
            add(Album("LILAC", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Butter", "방탄소년단", R.drawable.img_album_exp))
            add(Album("Next Level", "aespa", R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연 (TAEYEON)", R.drawable.img_album_exp6))
        }

        //리사이클러뷰에 어댑터 연결 및 레이아웃 매니저 설정
        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.recyclerHomeAlbum.adapter = albumRVAdapter
        binding.recyclerHomeAlbum.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{
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
        TabLayoutMediator(binding.tlIndicatorPanel, binding.homePanelBgIv){
            tab, position ->
            binding.homePanelBgIv.currentItem = tab.position
        }.attach()


        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp));
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2));
        binding.vpHomeIssue.adapter = bannerAdapter
        binding.vpHomeIssue.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }
}