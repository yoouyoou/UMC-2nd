package com.umc.project.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.project.flo.databinding.ItemLockerAlbumBinding

class LockerAlbumRVAdapter(): RecyclerView.Adapter<LockerAlbumRVAdapter.ViewHolder>() {

    private val albums = ArrayList<Album>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LockerAlbumRVAdapter.ViewHolder {
        val binding: ItemLockerAlbumBinding = ItemLockerAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LockerAlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albums[position])
        //아이템뷰 클릭리스너 추후 추가
    }

    override fun getItemCount(): Int = albums.size

    fun addAlbums(albums: ArrayList<Album>){
        this.albums.clear()
        this.albums.addAll(albums)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemLockerAlbumBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album){
            binding.ivItemAlbumImage.setImageResource(album.coverImg!!)
            binding.tvItemAlbumTitle.text = album.title
            binding.tvItemAlbumSinger.text = album.singer
        }
    }
}