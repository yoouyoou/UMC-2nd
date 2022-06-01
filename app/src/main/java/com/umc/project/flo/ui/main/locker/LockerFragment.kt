package com.umc.project.flo.ui.main.locker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.project.flo.databinding.FragmentLockerBinding
import com.umc.project.flo.ui.login.LoginActivity
import com.umc.project.flo.ui.main.MainActivity

class LockerFragment : Fragment() {
    lateinit var binding: FragmentLockerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        //로그인 페이지 이동
        binding.tvFragLockerLogin.setOnClickListener{
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        //뷰페이저에 어댑터 연결
        val adapter = LockerVPAdapter(this)
        binding.vpFragLocker.adapter = adapter
        binding.vpFragLocker.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //탭레이아웃과 뷰페이저 연결
        val menu = arrayListOf("저장한 곡", "음악파일", "저장앨범")
        TabLayoutMediator(binding.tlFragLockerMenu, binding.vpFragLocker){
            tab, position ->
            tab.text = menu[position]
        }.attach()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun getJwt():Int{
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt", 0)   //가져온값이 없으면 0
    }

    //뷰의 text를 로그인/로그아웃 상태확인 후 변환
   private fun initViews(){
       val jwt: Int = getJwt()
        if(jwt == 0) {
            binding.tvFragLockerLogin.text = "로그인"
            binding.tvFragLockerLogin.setOnClickListener{
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        } else{
            binding.tvFragLockerLogin.text = "로그아웃"
            binding.tvFragLockerLogin.setOnClickListener{
                //로그아웃 진행
                logout()
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
   }

    //로그아웃
    private fun logout(){
        //jwt를 0으로
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("jwt")    //key값이 jwt인 value를 제거
        editor.apply()
   }

}