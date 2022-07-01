package com.app.lastplayer

import android.support.v4.media.session.MediaControllerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.lastplayer.data.TrackSharedData
import com.app.lastplayer.ui.fragments.ControlFragment

interface ServiceConnector {
    val mediaController: MediaControllerCompat?
    var playbackStateChangedCallback: MainActivity.MediaCallback?
    var updateMetadataCallback: MainActivity.MetadataUpdateCallback?

    fun connectToService()
    fun onClickTrack(list: List<TrackSharedData>, currentPosition: Int?)
}