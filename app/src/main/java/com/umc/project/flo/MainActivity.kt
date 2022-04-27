package com.umc.project.flo

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.umc.project.flo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var song: Song = Song()
    private var gson : Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()

//        val song = Song(binding.mainPlayerSongTitle.text.toString(), binding.mainPlayerSongSinger.text.toString(),
//                        0, 60, false, "music_lilac")

        binding.mainPlayer.setOnClickListener{
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            intent.putExtra("music", song.music)
            startActivity(intent)
        }
    }

    private fun initBottomNavigation(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.homeFragment->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lockerFragment->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun setMiniPlayer(song: Song){
        binding.mainPlayerSongTitle.text = song.title
        binding.mainPlayerSongSinger.text = song.singer
        binding.mainSeekbar.progress = (song.second * 100000) / song.playTime
    }

    override fun onStart() {
        super.onStart()
        val sp = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = sp.getString("songData", null)

        song = if(songJson == null){        //처음 sharedPreference에 값이 없을 때
            Song("라일락", "아이유(IU)", 0, 60, false, "music_lilac")
        }else{
            gson.fromJson(songJson, Song::class.java)
        }

        setMiniPlayer(song)
    }

}