package com.umc.project.flo

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        inputDummySongs()
        inputDummyAlbums()
        initBottomNavigation()

//        val song = Song(binding.mainPlayerSongTitle.text.toString(), binding.mainPlayerSongSinger.text.toString(),
//                        0, 60, false, "music_lilac")

        binding.mainPlayer.setOnClickListener{
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()
            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
            /* 데이터를 넘길 때 이제 song의 id값만 넘기면 된다
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            intent.putExtra("music", song.music)
            startActivity(intent)
            */
        }
    }

    override fun onStart() {
        super.onStart()
        /* val sp = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = sp.getString("songData", null)

        song = if(songJson == null){        //처음 sharedPreference에 값이 없을 때
            Song("라일락", "아이유(IU)", 0, 60, false, "music_lilac")
        }else{
            gson.fromJson(songJson, Song::class.java)
        } */
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        val songDB = SongDatabase.getInstance(this)!!
        song = if(songId == 0){     //sharedPreference에서 가져온 값이 없을 때
            songDB.songDao().getSong(1) //첫번째 인덱스 가져오기
        }else{
            songDB.songDao().getSong(songId)
        }

        Log.d("song ID", song.id.toString())
        setMiniPlayer(song)
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

    //DB에 더미데이터 넣기
   private fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!! //songDB의 instance받아오기
        val songs = songDB.songDao().getSongs()

        //songs에 데이터가 있다면 함수 그냥 종료 비어있다면 더미데이터 넣기
        if(songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song("Butter", "BTS", 0, 230, false, "music_butter", R.drawable.img_album_exp, false)
        )
        songDB.songDao().insert(
            Song("LILAC", "아이유 (IU)", 0, 230, false, "music_lilac", R.drawable.img_album_exp2, false)
        )
        songDB.songDao().insert(
            Song("Next Level", "aespa", 0, 210, false, "music_next", R.drawable.img_album_exp3, false)
        )
        songDB.songDao().insert(
            Song("Boy with Luv", "BTS", 0, 210, false, "music_boy", R.drawable.img_album_exp4, false)
        )
        songDB.songDao().insert(
            Song("BBoom BBoom", "모모랜드", 0, 240, false, "music_bboom", R.drawable.img_album_exp5, false)
        )
        songDB.songDao().insert(
            Song("Weekend", "태연(TAEYEON)", 0, 200, false, "music_flu", R.drawable.img_album_exp6, false)
        )

        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
   }

    private fun inputDummyAlbums(){
        val songDB = SongDatabase.getInstance(this)!! //songDB의 instance받아오기
        val albums = songDB.albumDao().getAlbums()

        //albums에 데이터가 있다면 함수 그냥 종료 비어있다면 더미데이터 넣기
        if(albums.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(0, "IU 5th Album 'LILAC'", "아이유(IU)", R.drawable.img_album_exp2)
        )
        songDB.albumDao().insert(
            Album(1, "BUTTER", "방탄소년단(BTS)", R.drawable.img_album_exp)
        )
        songDB.albumDao().insert(
            Album(2, "iScreaM Vol.10 : Next Level Remixes", "에스파(AESPA)", R.drawable.img_album_exp3)
        )
        songDB.albumDao().insert(
            Album(3, "MAP OF THE SOUL : PERSONA", "방탄소년단(BTS)", R.drawable.img_album_exp4)
        )
        songDB.albumDao().insert(
            Album(4, "GREAT!", "모모랜드", R.drawable.img_album_exp5)
        )
    }

}