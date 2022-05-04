package com.umc.project.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.project.flo.databinding.ItemLockerSongBinding

class LockerRVAdapter(private val songList: ArrayList<Album>): RecyclerView.Adapter<LockerRVAdapter.ViewHolder>() {

    //아이템 뷰 객체(틀) 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemLockerSongBinding = ItemLockerSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    //뷰홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songList[position])
    }

    //데이터 크기 반환
    override fun getItemCount(): Int = songList.size

    inner class ViewHolder(val binding: ItemLockerSongBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(song: Album){
            binding.tvItemLockerTitle.text = song.title.toString()
            binding.tvItemLockerSinger.text = song.singer.toString()
            binding.ivItemLockerImage.setImageResource(song.coverImg!!)
        }
    }
}