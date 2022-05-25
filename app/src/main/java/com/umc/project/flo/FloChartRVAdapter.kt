package com.umc.project.flo

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.project.flo.databinding.ItemChartBinding

class FloChartRVAdapter(val context:Context, val result:FloChartResult): RecyclerView.Adapter<FloChartRVAdapter.ViewHolder>() {
    //result: api호출 성공시 받을 Result객체

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemChartBinding = ItemChartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(result.songs[position].coverImgUrl == "" || result.songs[position].coverImgUrl == null){
            //커버이미지가 비어있거나 널인 경우
        } else {
            Log.d("image",result.songs[position].coverImgUrl )
            Glide.with(context).load(result.songs[position].coverImgUrl).into(holder.coverImg)
        }
        holder.singer.text = result.songs[position].singer
    }

    override fun getItemCount(): Int = result.songs.size

    inner class ViewHolder(val binding: ItemChartBinding) : RecyclerView.ViewHolder(binding.root){
        val coverImg : ImageView = binding.imageView
        val singer: TextView = binding.textView
    }

}