package com.umc.project.flo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.umc.project.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ibSongBack.setOnClickListener{ finish() }

        binding.ivSongPlayerPlay.setOnClickListener{ setPlayerStatus(false) }
        binding.ivSongPlayerPause.setOnClickListener{ setPlayerStatus(true) }
    }

    fun setPlayerStatus(isPlaying:Boolean){
        if(isPlaying){
            binding.ivSongPlayerPlay.visibility = View.VISIBLE
            binding.ivSongPlayerPause.visibility = View.GONE
        }
        else{
            binding.ivSongPlayerPlay.visibility = View.GONE
            binding.ivSongPlayerPause.visibility = View.VISIBLE
        }
    }
}