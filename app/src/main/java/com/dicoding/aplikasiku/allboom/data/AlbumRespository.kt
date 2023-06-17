package com.dicoding.aplikasiku.allboom.data

import com.dicoding.aplikasiku.allboom.model.Album
import com.dicoding.aplikasiku.allboom.model.AlbumData

class AlbumRepository {
    fun getAlbums(): List<Album> {
        return AlbumData.album
    }

    fun searchAlbums(query: String): List<Album>{
        return AlbumData.album.filter {
            it.artist.contains(query, ignoreCase = true)
        }
    }
}