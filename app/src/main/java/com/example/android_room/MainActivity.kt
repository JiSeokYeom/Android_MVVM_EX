package com.example.android_room

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_room.adapter.MainRvAdapter
import com.example.android_room.databinding.ActivityMainBinding
import com.example.android_room.db.User
import com.example.android_room.viewModel.MainViewModel
import com.example.android_room.viewModel.SetViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // by lazy로 값을 선언 해준다.
    private val model: MainViewModel by lazy {
         ViewModelProvider(this).get(MainViewModel::class.java)
    }
   // private lateinit var model: MainViewModel
    private lateinit var binding : ActivityMainBinding
    private lateinit var rvAdapter: MainRvAdapter
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // DB 생성을 할 필요가 없어짐 ViewModel를 참조 할꺼라
       // db = UserDB.getInstance(this)!!

        rvAdapter = MainRvAdapter()


        binding.mainRV.apply {
            setHasFixedSize(true)
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }


        model.users.observe(this,{
            rvAdapter.mData = it
            rvAdapter.notifyDataSetChanged()
        })

        // 추가 버튼
        binding.addBtn.setOnClickListener {
            with(binding){
            val name = nameInput.text.toString()
            val age = ageInput.text.toString()
            val phone = phoneInput.text.toString()
                when {
                    nameInput.text.isNullOrBlank() -> {
                        Toast.makeText(this@MainActivity, "이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                    }
                    ageInput.text.isNullOrBlank() -> {
                        Toast.makeText(this@MainActivity, "나이를 입력해주세요", Toast.LENGTH_SHORT).show()
                    }
                    phoneInput.text.isNullOrBlank() -> {
                        Toast.makeText(this@MainActivity, "전화번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        // DB 접근은 Main thread에서 불가하므로 CoroutineScope 사용해 처리 한다
                        CoroutineScope(Dispatchers.IO).launch {
                            // 입력한 값 읽어와서 삽입 해주기
                            model.insert(User(name, age, phone))
                        }
                        nameInput.text.clear()
                        ageInput.text.clear()
                        phoneInput.text.clear()
                    }
                }
            }
        }

        // 삭제 버튼
        binding.delBtn.setOnClickListener {
            val name = binding.nameInput.text.toString()
            if(  binding.nameInput.text.isEmpty())
            {
                Toast.makeText(this, "삭제하실 이름을 입력하세요", Toast.LENGTH_SHORT).show()
            }
            else {
                CoroutineScope(Dispatchers.IO).launch {
                    model.deleteUserByName(name)
                }
                binding.nameInput.text.clear()
            }
        }

        // 전체삭제 버튼
        binding.delAllBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                model.deleteAll()
            }
        }


    }

}