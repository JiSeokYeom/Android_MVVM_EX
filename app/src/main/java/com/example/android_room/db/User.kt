package com.example.android_room.db

import androidx.room.Entity
import androidx.room.PrimaryKey

//만약 테이블 이름을 정해주고 싶으면 아래와 같이
//@Entity(tableName = "userProfile")

@Entity
data class User(
    // @PrimaryKey 기본키 직접 지정 방식
    // var id : Int = 0
    var name : String,
    var age : String,
    var Phone : String
){
    // 기본키 자동 생성 방식
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}
