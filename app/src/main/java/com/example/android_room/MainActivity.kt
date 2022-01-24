package com.example.android_room

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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
    private val model: MainViewModel by lazy { ViewModelProvider(this,
        SetViewModelFactory(application)
    ).get(MainViewModel::class.java) }
    private lateinit var binding : ActivityMainBinding
   // private lateinit var db : UserDB
   /* private lateinit var nameInput : EditText
    private lateinit var ageInput : EditText
    private lateinit var phoneInput : EditText
    private lateinit var addBtn: Button
    private lateinit var deleteBtn : Button
    private lateinit var deleteAllBtn : Button
    private lateinit var mainRV : RecyclerView*/
    private lateinit var dataList: List<User>
    private lateinit var rvAdapter: MainRvAdapter
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // DB 생성
       // db = UserDB.getInstance(this)!!

        rvAdapter = MainRvAdapter()


      //  model = ViewModelProvider(this,SetViewModelFactory()).get(MainViewModel::class.java)

   /*     nameInput = findViewById(R.id.name_input)
        ageInput = findViewById(R.id.age_input)
        phoneInput = findViewById(R.id.phone_input)
        addBtn = findViewById(R.id.add_Btn)
        mainRV = findViewById(R.id.mainRV)
        deleteBtn = findViewById(R.id.del_Btn)
        deleteAllBtn = findViewById(R.id.delAll_Btn)*/

        binding.mainRV.apply {
            setHasFixedSize(true)
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }


        model.users.observe(this, Observer {
            rvAdapter.mData = it
            rvAdapter.notifyDataSetChanged()
        })
        // DB 접근은 Main thread에서 불가하므로 CoroutineScope 사용해 처리 한다
//        CoroutineScope(Dispatchers.IO).launch {
//            // DB 읽어오고 Adapter 데이터에 추가 해주기
//            dataList = db.userDao().getAll()
//            rvAdapter.mData = dataList
//        }

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
                        CoroutineScope(Dispatchers.IO).launch {
                            // 입력한 값 읽어와서 삽입 해주기
                            model.insert(User(name, age, phone))
                            // DB에 있는 값 다 읽어와서 다시 Adapter 데이터에 추가 해주기
                           // dataList = db.userDao().getAll()
                        //    rvAdapter.mData = dataList
                        }
                        // 마지막 위치에서 삽입이 되었다고 Adapter에 알려줘서 갱신 시키기
                      //  rvAdapter.notifyItemInserted(dataList.size)

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
               //     dataList = db.userDao().getAll()
                //    rvAdapter.mData = dataList
                }
                rvAdapter.notifyDataSetChanged()
                binding.nameInput.text.clear()
            }
        }

        // 전체삭제 버튼
        binding.delAllBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                model.deleteAll()
           //     dataList = db.userDao().getAll()
            //    rvAdapter.mData = dataList
            }
            rvAdapter.notifyDataSetChanged()
        }


    }

}