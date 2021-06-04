package com.example.musicapplication

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private var player: MediaPlayerService? = null
    var serviceBound = false
    var audioList: ArrayList<Audio>? = ArrayList()
    private val REQ_DANGERS_PERMISSION = 2
    lateinit var adapter: MusicAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadAudio()
        initView()
    }

    fun initView() {
        recyclerView = findViewById(R.id.songsRV)
        adapter = audioList?.let { MusicAdapter(it,this) }!!
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()

        val layoutmanager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutmanager
    }


    override fun onResume() {
        super.onResume()
        requestPermissions(this)
    }

    //Binding this Client to the AudioPlayer Service
    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder: MediaPlayerService.LocalBinder = service as MediaPlayerService.LocalBinder
            player = binder.service
            serviceBound = true
            Toast.makeText(this@MainActivity, "Service Bound", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            serviceBound = false
        }
    }

    fun playAudio(media: String) {
        //Check is service is active
        if (!serviceBound) {
            val playerIntent = Intent(this, MediaPlayerService::class.java)
            playerIntent.putExtra("media", media)
            startService(playerIntent)
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE)
        } else {
            //Service is active
            //Send media with BroadcastReceiver
        }
    }

    private fun loadAudio() {
        val audio1 = Audio("Shakira", "Me Enamoré", "Me Enamoré", "Shakira")
        audioList?.add(audio1)
        val audio2 = Audio("", "When a Woman", "Me Enamoré", "Shakira")
        audioList?.add(audio2)
        val audio3 = Audio("", "Amarillo", "Me Enamoré", "Shakira")
        audioList?.add(audio3)
    }

    fun requestPermissions(activity: AppCompatActivity) {
        // Check if we have write permission
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            || PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.RECORD_AUDIO
            )
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO
                ),
                REQ_DANGERS_PERMISSION
            )
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putBoolean("ServiceState", serviceBound)
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        serviceBound = savedInstanceState.getBoolean("ServiceState")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (serviceBound) {
            unbindService(serviceConnection)
            //service is active
            player!!.stopSelf()
        }
    }
}