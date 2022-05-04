package com.umc.project.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.project.flo.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    private lateinit var mItemClickListener: MyItemClickListener        //전달받은 리스너 객체를 저장할 변수(어댑터 안에서 사용하기 위해)
    fun setMyItemClickListener(itemClickLister: MyItemClickListener){   //외부에서 전달받을 수 있는 함수
        mItemClickListener = itemClickLister
    }

    interface MyItemClickListener{  //어댑터 외부에서도 리사이클러뷰의 클릭리스너 이벤트를 사용하기 위해
        fun onItemClick(album: Album)
    }

    //처음에 화면에 보일 아이템뷰 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //사용하고자하는 아이템 뷰 객체 생성
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)  //뷰홀더에 넘기기
    }

    //뷰홀더에 데이터를 바인딩할 때마다 호출되는 함수
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick(albumList[position])
        }
    }

    //데이터크기를 알려주는 함수(리사이클러뷰가 마지막이 언제인지 알 수 있게 해주는 함수)
    override fun getItemCount(): Int {
        return albumList.size
    }

    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album){
            binding.tvItemAlbumTitle.text = album.title
            binding.tvItemAlbumSinger.text = album.singer
            binding.ivItemAlbumImage.setImageResource(album.coverImg!!)
        }
    }
}