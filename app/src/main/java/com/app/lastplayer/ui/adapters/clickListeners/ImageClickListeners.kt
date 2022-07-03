package com.app.lastplayer.ui.adapters.clickListeners

import com.app.lastplayer.data.TrackSharedData

fun interface ImageClickListener {
    fun onClick(itemId: String, imageUrl: String)
}

fun interface TrackClickListener {
    fun click(data: List<TrackSharedData>, position: Int)
}
