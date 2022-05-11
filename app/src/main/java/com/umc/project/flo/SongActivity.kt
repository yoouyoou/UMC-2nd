package com.umc.project.flo

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.umc.project.flo.databinding.ActivitySongBinding
import kotlin.system.exitProcess

//클래스를 다른 클래스로 상속을 받을 때는 소괄호를 넣어줘야 함
class SongActivity : AppCompatActivity() {
    //전역변수
    lateinit var binding : ActivitySongBinding
    //lateinit var song : Song -> songs[nowPos]로 변경
    lateinit var timer : Timer
    private var mediaplayer : MediaPlayer? = null
    private var gson : Gson = Gson()
    val songs = arrayListOf<Song>() //db에 들어있는 songList
    lateinit var songDB: SongDatabase
    var nowPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlayList()
        initSong()
        //setPlayer(song)

        binding.ibSongBack.setOnClickListener{ finish() }
        binding.ivSongPlayerPlay.setOnClickListener{ setPlayerStatus(true) }
        binding.ivSongPlayerPause.setOnClickListener{ setPlayerStatus(false) }
        binding.ivSongPlayerNext.setOnClickListener{ moveSong(1) }
        binding.ivSongPlayerPrevious.setOnClickListener{ moveSong(-1) }
        binding.ibSongLikeOff.setOnClickListener{ setLike(songs[nowPos].isLike) }
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

        songs[nowPos].isPlaying = false
        songs[nowPos].second = ((binding.seekbarSongPlayer.progress * songs[nowPos].playTime) / 100) / 1000
        setPlayerStatus(false)

        val sp = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sp.edit()  //에디터
        editor.putInt("songId", songs[nowPos].id)   //이제 song객체 자체가 아닌 id값을 sp에 저장해줌
//        val sogJson = gson.toJson(song)
//        editor.putString("songData", songJson)

        editor.apply()  //실제 저장작업 완료
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaplayer?.release()
        mediaplayer = null
    }

    //DB에서 값 가져와서 songs에 초기화
    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun setPlayerStatus(isPlaying:Boolean){
        songs[nowPos].isPlaying = isPlaying
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
        //3) sp에서 Song id값 받아와서 Songs와 비교해서 songList의 인덱스값 구하기
        val sp = getSharedPreferences("song", MODE_PRIVATE)
        val songId = sp.getInt("songId", 0)
        nowPos = getPlayingSongPosition(songId)
        Log.d("now Playing Song ID", songs[nowPos].id.toString())

        startTimer()
        setPlayer(songs[nowPos])

        /* 1) Intent로 값 하나하나 가져온 경우
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false),
                intent.getStringExtra("music")!!
            )
        }*/

        /* 2) 객체 직렬화 이용해서 객체를 sp로 담아서 가져옴
        val songJson = sp.getString("songData", null)
        song = if(songJson == null){        //처음 sharedPreference에 값이 없을 때
            Song("라일락", "아이유(IU)", 0, 60, false, "music_lilac")
        }else{
            gson.fromJson(songJson, Song::class.java)
        }
        */
    }

    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike, songs[nowPos].id)    //db값 업데이트해

        if(songs[nowPos].isLike)
            binding.ibSongLikeOff.setImageResource(R.drawable.ic_my_like_on)
        else
            binding.ibSongLikeOff.setImageResource(R.drawable.ic_my_like_off)
    }

    private fun moveSong(direct: Int){
        if(nowPos + direct < 0) {
            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
            return
        }
        if(nowPos + direct >= songs.size){
            Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
            return
        }
        nowPos += direct
        timer.interrupt() //원래 진행하던 타이머 스레드를 멈추고
        startTimer()      //스레드 재실행

        mediaplayer?.release()   //미디어플레이어도 새로운 노래를 재생해야하므로 해제
        mediaplayer = null
        setPlayer(songs[nowPos]) //다시 데이터 렌더링
    }

    private fun getPlayingSongPosition(songId: Int): Int{
        for(i in 0 until songs.size){
            if(songs[i].id == songId)
                return i
        }
        return 0
    }

    //Song액티비티 화면을 받아와서 초기화된 song에 대한 데이터 정보를 렌더링
    private fun setPlayer(song: Song){
        binding.tvSongTitle.text = song.title
        binding.tvSongSinger.text = song.singer
        binding.tvSongProgressTime.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.tvSongTotalTime.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.ivSongAlbumImage.setImageResource(song.coverImg!!)
        binding.seekbarSongPlayer.progress = (song.second * 1000 / song.playTime)

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaplayer = MediaPlayer.create(this, music)

        //song데이터에 따라 좋아요 표시 구현
        if(song.isLike)
            binding.ibSongLikeOff.setImageResource(R.drawable.ic_my_like_on)
        else
            binding.ibSongLikeOff.setImageResource(R.drawable.ic_my_like_off)

        setPlayerStatus(song.isPlaying)
    }

    private fun startTimer(){
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
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