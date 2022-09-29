package com.example.musicplayer2.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer2.MainActivity
import com.example.musicplayer2.R

class MyAdapter(val activity: MainActivity,val musicData:ArrayList<MusicData>):RecyclerView.Adapter<ViewHolder>() {

    var isOnclik:((MusicData)-> Unit)? = null
    lateinit var viewModel: ViewModel
    var index:Int = 0
    var rowIndex = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
        return ViewHolder(v,parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(musicData[position])
        viewModel = ViewModelProvider(activity).get(ViewModel::class.java)
        val musicData = musicData[position]
        holder.itemView.setOnClickListener {
            rowIndex = position
            notifyDataSetChanged()
            if (viewModel.index.value == position){
                isOnclik?.invoke(musicData)
            }else{
                isOnclik?.invoke(musicData)
                viewModel.index.value = position
                index = position
            }
        }
        if (rowIndex == position){
            holder.title.setTextColor(Color.parseColor("#05E300"))
        }else{
            holder.title.setTextColor(Color.parseColor("#ffffff"))
        }
        holder.like.setOnClickListener {
            holder.like.setImageResource(R.drawable.heart)
            Toast.makeText(activity,"You liked the song",Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return musicData.size
    }
}