package com.example.android_room.viewModel


import android.app.Application
import androidx.lifecycle.*
import com.example.android_room.db.User
import com.example.android_room.db.UserDB
import com.example.android_room.db.UserDao
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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
