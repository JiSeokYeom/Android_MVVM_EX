package com.example.android_room.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.android_room.db.User
import com.example.android_room.db.UserDB
import com.example.android_room.db.UserDao

class MainViewModel(application: Application) : AndroidViewModel(Application()) {
    var users : LiveData<List<User>>
    private var userDao : UserDao
    init {
        val db = UserDB.getInstance(application)
        userDao = db!!.userDao()
        users = db.userDao().getAll()
    }
    fun insert(user : User){
        userDao.insert(user)
    }

    fun deleteAll(){
        userDao.deleteAll()
    }

    fun deleteUserByName(name : String){
        userDao.deleteUserByName(name)
    }

}
