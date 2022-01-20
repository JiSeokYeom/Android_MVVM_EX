package com.example.android_room.db

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insert(user : User)

    @Update
    fun update(user : User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM User") // 테이블의 모든 값을 가져와라
    fun getAll() : List<User>

    @Query("DELETE FROM User WHERE name = :name") // 'name'에 해당하는 유저를 삭제해라
    fun deleteUserByName(name : String)

    @Query("DELETE FROM User") // 전체 테이블 값 삭제
    fun deleteAll()
}