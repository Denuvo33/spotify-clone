package com.example.musicplayer2.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.musicplayer2.R
import kotlinx.coroutines.NonDisposableHandle.parent

class ViewHolder(inflater: LayoutInflater,parent:ViewGroup):RecyclerView.ViewHolder(inflater.inflate(R.layout.music_list,parent,false)) {

    val title: TextView = itemView.findViewById(R.id.txt_title)
    val img : ImageView = itemView.findViewById(R.id.img_msc)
    val band : TextView = itemView.findViewById(R.id.txt_band)
    val like : ImageView = itemView.findViewById(R.id.like)

    fun bind(data: MusicData){
        title.text = data.title
        band.text = data.band
        img.load(data.img)
        like.setImageResource(R.drawable.heartw)
    }

}