package com.umc.project.flo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlbumDao {  //LikeTable과 조인해서 사용

    @Insert
    fun insert(album: Album)

    @Query("SELECT * FROM AlbumTable")
    fun getAlbums(): List<Album>

    @Insert
    fun likedAlbum(like: Like)

   //현재사용자가 이 앨범을 좋아요를 눌렀는지 안눌렀는지 확인 (앨범프래그먼트로 넘어갈 때 사용)
   //파라미터로 넘어온 유저id와 앨범id가 likeTable에 있는지 확인 후 좋아요id값 반환
    @Query("SELECT id FROM LikeTable WHERE userId=:userId AND albumId=:albumId")
    fun isLikedAlbum(userId:Int, albumId:Int) : Int?

    //좋아요 취소
    @Query("DELETE FROM LikeTable WHERE userId=:userId AND albumId=:albumId")
    fun disLikedAlbum(userId: Int, albumId: Int)

    //보관함에서 유저를 구분하여 좋아요한 앨범 정보들 조회
    @Query("SELECT AT.* FROM LikeTable as LT LEFT JOIN AlbumTable as AT ON LT.albumId=AT.id WHERE LT.userId=:userId")
    fun getLikedAlbums(userId: Int) : List<Album>

}