package com.example.android_room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// entities : 이 DB에 어떤 테이블들이 있는지 명시
// version : Scheme가 바뀔 때 이 version도 바뀌어야 함
@Database(entities = [User::class], version = 1)
abstract class UserDB : RoomDatabase() {
    abstract fun userDao():UserDao

    companion object{
        private var instance : UserDB? = null

        @Synchronized
        fun getInstance(context: Context):UserDB?{
            if (instance == null) {
                synchronized(UserDB::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDB::class.java,
                        "user-database"
                    ).build()
                }
            }
            return instance
        }
    }
}