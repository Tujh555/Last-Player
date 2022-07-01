package com.app.lastplayer.media

import android.support.v4.media.session.MediaSessionCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player

class ExoPlayerListener(
    private val mediaSessionCallback: MediaSessionCompat.Callback?,
    private val player: ExoPlayer?
) : Player.Listener {
    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)

        if (player?.playWhenReady == true && playbackState == ExoPlayer.STATE_ENDED) {
            mediaSessionCallback?.onSkipToNext()
        }
    }
}