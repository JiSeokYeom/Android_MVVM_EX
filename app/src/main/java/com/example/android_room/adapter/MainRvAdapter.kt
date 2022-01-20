package com.example.android_room.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_room.R
import com.example.android_room.db.User

class MainRvAdapter : RecyclerView.Adapter<MainRvAdapter.ViewHolder>(){
    var mData = listOf<User>()

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val itemName = itemView.findViewById<TextView>(R.id.name_output)
        private val itemAge = itemView.findViewById<TextView>(R.id.age_output)
        private val itemPhone = itemView.findViewById<TextView>(R.id.phone_output)

        fun setData(data : User){
            itemName.text = data.name
            itemAge.text = data.age
            itemPhone.text = data.Phone
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRvAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item,parent,false))
    }

    override fun onBindViewHolder(holder: MainRvAdapter.ViewHolder, position: Int) {
       val item  = mData[position]
        holder.setData(item)
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}