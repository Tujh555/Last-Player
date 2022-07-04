package com.app.lastplayer.ui.adapters.clickListeners

import com.app.lastplayer.data.TrackSharedData
import com.app.lastplayer.database.entities.TrackEntity

fun interface ImageClickListener {
    fun onClick(itemId: String, imageUrl: String)
}

fun interface TrackClickListener {
    fun click(data: List<TrackSharedData>, position: Int)
}

fun interface AddToFavoritesClickListener {
    fun click(trackEntity: TrackEntity)
}

fun interface RemoveFromFavorites {
    fun click(trackEntity: TrackEntity)
}