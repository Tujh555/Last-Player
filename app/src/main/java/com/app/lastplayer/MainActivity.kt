package com.app.lastplayer

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.lastplayer.data.TrackSharedData
import com.app.lastplayer.media.PlaybackService
import com.app.lastplayer.ui.fragments.ControlFragment


class MainActivity : AppCompatActivity(), ServiceConnector {
    private var playbackBinder: PlaybackService.PlaybackServiceBinder? = null
    private var _mediaController: MediaControllerCompat? = null
    private var playbackService: PlaybackService? = null
    private val currentTrackList = mutableListOf<TrackSharedData>()
    private var currentPosition = 0

    private var serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            playbackBinder = p1 as PlaybackService.PlaybackServiceBinder
            Log.d("MyLogs", "MainActivity mediaController = $_mediaController")

            try {
                playbackBinder?.let { binder ->
                    binder.updateMetadata = updateMetadataCallback

                    _mediaController = MediaControllerCompat(
                        baseContext,
                        binder.mediaSessionToken
                    )

                    Log.d("MyLogs", "MainActivity mediaController = $_mediaController")
                    _mediaController?.registerCallback(mediaControllerCallback)

                    mediaControllerCallback.onPlaybackStateChanged(
                        _mediaController?.playbackState
                    )

                    playbackService = playbackBinder?.getService() as PlaybackService
                    playbackService?.refreshTrackList(currentTrackList, currentPosition)
                    _mediaController?.transportControls?.play()

                    Log.d("MyLogs", "in serviceConnection $binder")
                }
            } catch (e: RemoteException) {
                Log.d("MyLogs", e.toString())
                _mediaController = null
            }
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            playbackBinder = null
            Log.d("MyLogs", "onServiceDisconnected")

            if (_mediaController != null) {
                _mediaController?.unregisterCallback(mediaControllerCallback)
                _mediaController = null
            }
        }
    }

    override var playbackStateChangedCallback: MediaCallback? = null
    override var updateMetadataCallback: MetadataUpdateCallback? = null

    private val mediaControllerCallback = object : MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)
            playbackStateChangedCallback?.onStateChanged(state)
        }
    }

    override val mediaController: MediaControllerCompat?
        get() = _mediaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun connectToService() {
        bindService(
            Intent(this, PlaybackService::class.java),
            serviceConnection,
            BIND_AUTO_CREATE
        )

        Log.d("MyLogs", "MainActivity connectToService()")
    }

    override fun onClickTrack(list: List<TrackSharedData>, currentPosition: Int?) {
        if (playbackService == null) {
            connectToService()
            val controlFragment = ControlFragment.newInstance()

            supportFragmentManager.beginTransaction()
                .replace(R.id.controlFragmentContainer, controlFragment)
                .commit()

            currentTrackList.addAll(list)
            this.currentPosition = currentPosition ?: 0

            return
        }

        playbackService?.refreshTrackList(list, currentPosition)
        _mediaController?.transportControls?.play()
    }

    override fun onDestroy() {
        super.onDestroy()

        playbackBinder = null

        if (_mediaController != null) {
            _mediaController?.unregisterCallback(mediaControllerCallback)
            _mediaController = null
        }

        unbindService(serviceConnection)
    }

    fun interface MediaCallback {
        fun onStateChanged(state: PlaybackStateCompat?)
    }

    fun interface MetadataUpdateCallback {
        fun onMetadataUpdate(track: TrackSharedData)
    }
}