package com.example.musicapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MusicAdapter(var mAudio: List<Audio>, private  var context: Context): RecyclerView.Adapter<MusicAdapter.ViewHolder>(){

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView)
    {
        val view: View = mView
        internal var play_button: ImageButton
        internal var txt_title: TextView

        init {
            play_button = view.findViewById(R.id.playBtn)
            txt_title = view.findViewById(R.id.titleTV)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.song_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_title.setText(mAudio[position].getTitle())
        holder.play_button.setOnClickListener(View.OnClickListener {
            if (context is MainActivity) {
                mAudio[position].getData()?.let { it1 -> (context as MainActivity).playAudio(it1) }
            }
        })
    }

    override fun getItemCount(): Int {
        return mAudio.size
    }
}