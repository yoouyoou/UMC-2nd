package com.umc.project.flo

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.umc.project.flo.databinding.ActivitySongBinding
import kotlin.system.exitProcess

//클래스를 다른 클래스로 상속을 받을 때는 소괄호를 넣어줘야 함
class SongActivity : AppCompatActivity() {
    //전역변수
    lateinit var binding : ActivitySongBinding
    lateinit var song : Song
    lateinit var timer : Timer
    private var mediaplayer : MediaPlayer? = null
    private var gson : Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()
        setPlayer(song)

        binding.ibSongBack.setOnClickListener{ finish() }
        binding.ivSongPlayerPlay.setOnClickListener{ setPlayerStatus(true) }
        binding.ivSongPlayerPause.setOnClickListener{ setPlayerStatus(false) }
//        binding.ibSongInfo.setOnClickListener{
//            val dialog = BottomSheetDialog(applicationContext)
//            dialog.setContentView(R.layout.item_bottom_dialog_album_list)
//            dialog.setCanceledOnTouchOutside(true)
//            dialog.create()
//            dialog.show()
//        }
    }

    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        song.second = ((binding.seekbarSongPlayer.progress * song.playTime) / 100) / 1000
        val sp = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sp.edit()
        val songJson = gson.toJson(song)
        editor.putString("songData", songJson)
        editor.apply()  //실제 저장작업 완료
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaplayer?.release()
        mediaplayer = null
    }

    private fun setPlayerStatus(isPlaying:Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.ivSongPlayerPlay.visibility = View.GONE
            binding.ivSongPlayerPause.visibility = View.VISIBLE
            mediaplayer?.start()
        }
        else{
            binding.ivSongPlayerPlay.visibility = View.VISIBLE
            binding.ivSongPlayerPause.visibility = View.GONE
            if(mediaplayer?.isPlaying == true)
                mediaplayer?.pause()
        }
    }

    private fun initSong(){
        val sp = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = sp.getString("songData", null)

//        if(intent.hasExtra("title") && intent.hasExtra("singer")){
//            song = Song(
//                intent.getStringExtra("title")!!,
//                intent.getStringExtra("singer")!!,
//                intent.getIntExtra("second", 0),
//                intent.getIntExtra("playTime", 0),
//                intent.getBooleanExtra("isPlaying", false),
//                intent.getStringExtra("music")!!
//            )
//        }
        song = if(songJson == null){        //처음 sharedPreference에 값이 없을 때
            Song("라일락", "아이유(IU)", 0, 60, false, "music_lilac")
        }else{
            gson.fromJson(songJson, Song::class.java)
        }

        startTimer()
    }

    //Song액티비티 화면을 받아와서 초기화된 song에 대한 데이터 정보를 렌더링
    private fun setPlayer(song: Song){
        binding.tvSongTitle.text = song.title
        binding.tvSongSinger.text = song.singer
        binding.tvSongProgressTime.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.tvSongTotalTime.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.seekbarSongPlayer.progress = (song.second * 1000 / song.playTime)

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaplayer = MediaPlayer.create(this, music)
        setPlayerStatus(song.isPlaying)
    }

    private fun startTimer(){
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    //시간이 지남에 따라 seekbar와 타이머를 바꿔줘야하므로 binding변수를 사용해야 한다 -> 이너클래스
    //총시간, 진행 중인지 여부
    inner class Timer(private val playTime:Int, var isPlaying: Boolean=true):Thread(){
        private var second : Int = 0
        private var mills : Float = 0f

        override fun run() {
            super.run()
            try{
                while(true){
                    if(second >= playTime)
                        break

                    if(isPlaying){
                        sleep(50)
                        mills += 50

                        runOnUiThread { binding.seekbarSongPlayer.progress = ((mills/playTime) * 100).toInt() }

                        if(mills % 1000 == 0f){
                            runOnUiThread{ binding.tvSongProgressTime.text = String.format("%02d:%02d", second / 60, second % 60) }
                            second++ 
                        }

                    }
                }
            } catch (e: InterruptedException){
                Log.d("SongActivity", "스레드 종료. ${e.message}")
            }
        }

    }
}