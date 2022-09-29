package com.example.musicplayer2

import android.app.*
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.musicplayer2.adapter.MusicData
import com.example.musicplayer2.adapter.MyAdapter
import com.example.musicplayer2.adapter.ViewModel
import com.example.musicplayer2.databinding.ActivityMainBinding
import com.google.firebase.firestore.*
import com.google.firebase.firestore.auth.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mp : MediaPlayer? = null
    var ispause = false
    var isMusicPlay = false
    var musicList = ArrayList<MusicData>()
    lateinit var myAdapter: MyAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var db: FirebaseFirestore
    var id:String = "0"
    lateinit var viewModel: ViewModel
    var isInMusicView = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        //SetRecyclerView
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        musicList = arrayListOf()
        myAdapter = MyAdapter(this,musicList)
        recyclerView.adapter = myAdapter
        addSong()
        myAdapter.isOnclik = {
            binding.msc.visibility = View.VISIBLE
            binding.txtTitleInside.text = it.title
            binding.imgInside.load(it.img)
            isInMusicView = true
            //Get Song url from dataClass
            val song = Uri.parse(it.url)
            if (id == it.id){

            }else{
                mp?.stop()
                mp?.reset()
                mp?.release()
                //Call Song to play
                controlSound(song)
                id = it.id.toString()
            }
            binding.txtTitleBtm.text = it.title
            binding.imgBtm.load(it.img)

        }
        //Skip next Or Prev
        binding.imgBtmfrwrd.setOnClickListener {
            var musicSize = musicList.size
            musicSize--
            if (myAdapter.index == musicSize){
                mp?.stop()
                mp?.reset()
                mp?.release()
                var index = 0
                var data  = musicList.get(index)
                var song = Uri.parse(data.url)
                binding.txtTitleInside.text = data.title
                binding.imgInside.load(data.img)
                controlSound(song)
                myAdapter.index = index
                binding.txtTitleBtm.text = data.title
                binding.imgBtm.load(data.img)
            }else{
                mp?.stop()
                mp?.reset()
                mp?.release()
                var index = myAdapter.index
                index++
                var data  = musicList.get(index)
                var song = Uri.parse(data.url)
                binding.txtTitleInside.text = data.title
                binding.imgInside.load(data.img)
                controlSound(song)
                myAdapter.index = index
                binding.txtTitleBtm.text = data.title
                binding.imgBtm.load(data.img)
            }

        }

        binding.imgBtmpref.setOnClickListener {
            if (myAdapter.index == 0){
                mp?.stop()
                mp?.reset()
                mp?.release()
                var data  = musicList.size
                data--
                var music = musicList.get(data)
                var song = Uri.parse(music.url)
                controlSound(song)
                myAdapter.index = data
                binding.txtTitleInside.text = music.title
                binding.imgInside.load(music.img)
                binding.txtTitleBtm.text = music.title
                binding.imgBtm.load(music.img)

            }else{
                mp?.stop()
                mp?.reset()
                mp?.release()
                var index = myAdapter.index
                index--
                var data  = musicList.get(index)
                var song = Uri.parse(data.url)
                binding.txtTitleInside.text = data.title
                binding.imgInside.load(data.img)
                controlSound(song)
                myAdapter.index = index
                binding.txtTitleBtm.text = data.title
                binding.imgBtm.load(data.img)
            }
        }

        binding.frward.setOnClickListener {
            var musicSize = musicList.size
            musicSize--
            if (myAdapter.index == musicSize){
                mp?.stop()
                mp?.reset()
                mp?.release()
                var index = 0
                var data  = musicList.get(index)
                var song = Uri.parse(data.url)
                binding.txtTitleInside.text = data.title
                binding.imgInside.load(data.img)
                controlSound(song)
                myAdapter.index = index
                binding.txtTitleBtm.text = data.title
                binding.imgBtm.load(data.img)
            }else{
                mp?.stop()
                mp?.reset()
                mp?.release()
                var index = myAdapter.index
                index++
                var data  = musicList.get(index)
                var song = Uri.parse(data.url)
                binding.txtTitleInside.text = data.title
                binding.imgInside.load(data.img)
                controlSound(song)
                myAdapter.index = index
                binding.txtTitleBtm.text = data.title
                binding.imgBtm.load(data.img)
            }

        }

        binding.bckwrd.setOnClickListener {
            if (myAdapter.index == 0){
                mp?.stop()
                mp?.reset()
                mp?.release()
                var data  = musicList.size
                data--
                var music = musicList.get(data)
                var song = Uri.parse(music.url)
                controlSound(song)
                myAdapter.index = data
                binding.txtTitleInside.text = music.title
                binding.imgInside.load(music.img)
                binding.txtTitleBtm.text = music.title
                binding.imgBtm.load(music.img)

            }else{
                mp?.stop()
                mp?.reset()
                mp?.release()
                var index = myAdapter.index
                index--
                var data  = musicList.get(index)
                var song = Uri.parse(data.url)
                binding.txtTitleInside.text = data.title
                binding.imgInside.load(data.img)
                controlSound(song)
                myAdapter.index = index
                binding.txtTitleBtm.text = data.title
                binding.imgBtm.load(data.img)
            }
        }


        binding.arrowback.setOnClickListener {
            binding.msc.visibility = View.GONE
            isInMusicView = false
            if (isMusicPlay){
                binding.btmMsc.visibility = View.VISIBLE
            }else{
                binding.btmMsc.visibility = View.GONE
            }
        }
        binding.btmMsc.setOnClickListener {
            binding.msc.visibility = View.VISIBLE
            isInMusicView = true
        }


    }

    override fun onBackPressed() {
        if (isInMusicView){
            binding.msc.visibility = View.GONE
            binding.btmMsc.visibility = View.VISIBLE
            isInMusicView = false
        }else{
            super.onPause()
        }
    }


    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }


    private fun addSong() {
        db = FirebaseFirestore.getInstance()
        db.collection("music").
        addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {
                if (error != null){
                    Log.e("MainActivity",error.message.toString())
                    return
                }

                for (dc:DocumentChange in value?.documentChanges!!){
                    if (dc.type == DocumentChange.Type.ADDED){
                        musicList.add(dc.document.toObject(MusicData::class.java))
                        binding.progress.visibility = View.GONE
                    }
                }
                myAdapter.notifyDataSetChanged()
            }
        })
    }


    private fun controlSound(uri: Uri) {
        //init MediaPlayer
        mp = MediaPlayer.create(this,uri)
        initSeekbar()
        mp?.start()
        isMusicPlay = true
        binding.imgBtmPause.setOnClickListener {
            if (ispause){
                binding.imgBtmPause.setImageResource(R.drawable.ic_baseline_pause)
                if (mp == null){
                    mp = MediaPlayer.create(this,uri)
                    initSeekbar()
                }
                mp?.start()
                ispause = false
                isMusicPlay = true
            }else{
                binding.imgBtmPause.setImageResource(R.drawable.ic_baseline_play_arrow)
                if (mp != null)mp?.pause()
                ispause = true
            }
        }
        binding.startmsc.setOnClickListener {
            if (ispause){
                binding.startmsc.setImageResource(R.drawable.ic_baseline_pause)
                if (mp == null){
                    mp = MediaPlayer.create(this,uri)
                    initSeekbar()
                }
                mp?.start()
                ispause = false
                isMusicPlay = true
            }else{
                binding.startmsc.setImageResource(R.drawable.ic_baseline_play_arrow)
                if (mp != null)mp?.pause()
                ispause = true
            }
        }

        binding.stopmsc.setOnClickListener {
            if (mp != null){
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
                ispause = true
                isMusicPlay = false
                binding.startmsc.setImageResource(R.drawable.ic_baseline_play_arrow)
            }
        }

        mp!!.setOnCompletionListener {
            Log.d("TAG","Song end")
            mp?.stop()
            mp?.reset()
            mp?.release()
        }

        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun initSeekbar() {
        binding.seekbar.max = mp!!.duration
        val handler = Handler()
        handler.postDelayed(object :Runnable{
            override fun run() {
                try {
                    binding.seekbar.progress = mp!!.currentPosition
                    handler.postDelayed(this,1000)
                }catch (e:Exception){

                    binding.seekbar.progress = 0
                }
            }
        },0)
    }
}
