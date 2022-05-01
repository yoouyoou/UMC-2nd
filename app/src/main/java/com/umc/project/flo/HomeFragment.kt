package com.umc.project.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.project.flo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.linearSongAlbumContent.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, AlbumFragment())
                .commitAllowingStateLoss()
        }

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