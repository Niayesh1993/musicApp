package com.example.musicapplication

import java.io.Serializable

class Audio(data: String, title: String, album: String, artist: String) : Serializable {
    private var data: String? = null
    private var title: String? = null
    private var album: String? = null
    private var artist: String? = null

    init {
        this.data = data
        this.title = title
        this.album = album
        this.artist = artist
    }

    fun getData(): String? {
        return data
    }

    fun setData(data: String?) {
        this.data = data
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getAlbum(): String? {
        return album
    }

    fun setAlbum(album: String?) {
        this.album = album
    }

    fun getArtist(): String? {
        return artist
    }

    fun setArtist(artist: String?) {
        this.artist = artist
    }
}