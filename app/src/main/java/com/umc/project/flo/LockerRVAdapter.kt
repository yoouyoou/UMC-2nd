package com.umc.project.flo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.project.flo.databinding.ItemLockerSongBinding

class LockerRVAdapter(private val songList: ArrayList<Album>): RecyclerView.Adapter<LockerRVAdapter.ViewHolder>() {

    private val songs = ArrayList<Song>()
    interface MyItemClickListener{
        fun onRemoveSong(songId: Int)
    }
    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    //아이템 뷰 객체(틀) 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemLockerSongBinding = ItemLockerSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    //뷰홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songList[position])
        //클릭이벤트
        holder.binding.ibItemLockerMore.setOnClickListener{
            mItemClickListener.onRemoveSong(songs[position].id)
            removeSong(position)
        }
    }

    //데이터 크기 반환
    override fun getItemCount(): Int = songList.size

    //좋아요한 노래들을 담는 함수
    fun addSongs(songs: ArrayList<Song>){
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }

    //보관함 노래 지우는 함수
    private fun removeSong(position: Int){
        songs.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemLockerSongBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(song: Album){
            binding.tvItemLockerTitle.text = song.title.toString()
            binding.tvItemLockerSinger.text = song.singer.toString()
            binding.ivItemLockerImage.setImageResource(song.coverImg!!)
        }
    }
}