package com.umc.project.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.umc.project.flo.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding
    private var gson: Gson = Gson()
    private var isLiked: Boolean = false    //현재 들어온 앨범이 좋아요 눌러졌는지 여부

    private val information = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        //Home에서 넘어온 데이터 받아오기
        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)

        isLiked = isLikedAlbum(album.id)
        setInit(album)
        setOnClickListeners(album)


        binding.ivAlbumBack.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()
        }

        val albumAdapter = AlbumVPAdapter(this)
        binding.vpAlbumContent.adapter = albumAdapter
        TabLayoutMediator(binding.tlAlbumTab, binding.vpAlbumContent){
            tab, position -> tab.text = information[position]
        }.attach()

        return binding.root
    }

    //뷰 초기화
    private fun setInit(album: Album){
        binding.tvAlbumTitle.text = album.title.toString()
        binding.tvAlbumSinger.text = album.singer.toString()
        binding.ivAlbumImage.setImageResource(album.coverImg!!)
        //앨범프래그먼트에서 좋아요 눌렀을 때 하트변경
        if(isLiked)
            binding.ivSongAlbumLike.setImageResource(R.drawable.ic_my_like_on)
        else
            binding.ivSongAlbumLike.setImageResource(R.drawable.ic_my_like_off)

    }

    //현재 로그인되어있는 사용자를 알기위해
    private fun getJwt(): Int{
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt", 0)
    }

    //앨범 좋아요시 DB변경
    private fun likedAlbum(userId: Int, albumId: Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId, albumId)
        songDB.albumDao().likedAlbum(like) //좋아요 눌렀을 때 LikeTable에 정보추가
    }

    //좋아요 취소하면 LikeTable에서 현재 유저id와 앨범id가 같은 것을 지움
    private fun disLikedAlbum(albumId: Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()
        songDB.albumDao().disLikedAlbum(userId, albumId)
    }

    //Home프래그먼트에서 Album프래그먼트로 이동시에 이 앨범에 내가 좋아요를 눌렀는지 여부
    private fun isLikedAlbum(albumId: Int): Boolean{
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()
        val likeId: Int? = songDB.albumDao().isLikedAlbum(userId, albumId)

        //만약 유저가 이 앨범을 좋아요했으면 likeId가 null이 아니므로 true가 됨
        return likeId != null
    }

    private fun setOnClickListeners(album: Album){
        val userId = getJwt()
        binding.ivSongAlbumLike.setOnClickListener{
            if(isLiked){    //좋아요가 눌러져있을 때
                binding.ivSongAlbumLike.setImageResource(R.drawable.ic_my_like_off)
                isLiked = false
                disLikedAlbum(album.id)
            }
            else{
                binding.ivSongAlbumLike.setImageResource(R.drawable.ic_my_like_on)
                isLiked = true
                likedAlbum(userId, album.id)
            }
        }
    }

}