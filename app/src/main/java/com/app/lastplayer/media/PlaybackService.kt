package com.app.lastplayer.media

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.MutableLiveData
import androidx.media.MediaBrowserServiceCompat
import androidx.media.session.MediaButtonReceiver
import com.app.lastplayer.MainActivity
import com.app.lastplayer.R
import com.app.lastplayer.appComponent
import com.app.lastplayer.data.TrackSharedData
import com.app.lastplayer.repositories.MusicRepository
import com.bumptech.glide.RequestManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSource
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.util.Util
import okhttp3.OkHttpClient
import javax.inject.Inject


class PlaybackService : MediaBrowserServiceCompat() {
    private var mediaSession: MediaSessionCompat? = null
    private var audioFocusRequest: AudioFocusRequest? = null
    private var audioFocusRequested = false
    private var audioManager: AudioManager? = null
    private var exoPlayer: ExoPlayer? = null
    private var extractorsFactory: ExtractorsFactory? = null
    private var dataSourceFactory: DataSource.Factory? = null
    private var trackDuration = 0L

    @Inject
    lateinit var stateBuilder: PlaybackStateCompat.Builder

    @Inject
    lateinit var metadataBuilder: MediaMetadataCompat.Builder

    @Inject
    lateinit var appContext: Context

    @Inject
    lateinit var musicRepository: MusicRepository

    @Inject
    lateinit var glideRequestManager: RequestManager

    @Inject
    lateinit var cache: Cache

    private val becomingNoisyReceiver = BecomingNoisyReceiver()
    private val localBinder = PlaybackServiceBinder()

    private val mediaSessionCallback = object : MediaSessionCompat.Callback() {
        var currentState = PlaybackStateCompat.STATE_STOPPED
        private var currentUrl = ""

        override fun onSeekTo(pos: Long) {
            exoPlayer?.seekTo(pos)
        }

        override fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {
            super.onPlayFromMediaId(mediaId, extras)
            mediaId?.let { id ->
                playTrack(musicRepository.getTrackByIndex(id.toInt()))
            }
        }

        override fun onPlay() {
            super.onPlay()
            startForegroundService(Intent(applicationContext, PlaybackService::class.java))
            playTrack(musicRepository.currentTrack)
        }

        private fun playTrack(track: TrackSharedData) {
            updateMetadataFromTrack(track)

            prepareToPlay(track.trackUrl)

            if (exoPlayer?.playWhenReady != true) {
                if (!audioFocusRequested) {
                    audioFocusRequested = true

                    val audioFocusResult = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        audioFocusRequest?.let { audioManager?.requestAudioFocus(it) }
                    } else {
                        audioManager?.requestAudioFocus(
                            audioFocusChangeListener,
                            AudioManager.STREAM_MUSIC,
                            AudioManager.AUDIOFOCUS_GAIN
                        )
                    }

                    if (audioFocusResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                        return
                }

                mediaSession?.isActive = true
                registerReceiver(
                    becomingNoisyReceiver,
                    IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY)
                )

                exoPlayer?.playWhenReady = true
            }

            mediaSession?.setPlaybackState(
                stateBuilder.setState(
                    PlaybackStateCompat.STATE_PLAYING,
                    PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN,
                    1f
                ).build()
            )

            currentState = PlaybackStateCompat.STATE_PLAYING

            refreshNotificationAndForegroundStatus(currentState)
        }

        override fun onPause() {
            super.onPause()

            if (exoPlayer?.playWhenReady == true) {
                exoPlayer?.playWhenReady = false
                unregisterReceiver(becomingNoisyReceiver)
            }

            mediaSession?.setPlaybackState(
                stateBuilder.setState(
                    PlaybackStateCompat.STATE_PAUSED,
                    PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN,
                    1f
                ).build()
            )

            currentState = PlaybackStateCompat.STATE_PAUSED
            refreshNotificationAndForegroundStatus(currentState)
        }

        override fun onStop() {
            super.onStop()

            if (exoPlayer?.playWhenReady == true) {
                exoPlayer?.playWhenReady = false
                unregisterReceiver(becomingNoisyReceiver)
            }

            if (audioFocusRequested) {
                audioFocusRequested = false

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    audioFocusRequest?.let {
                        audioManager?.abandonAudioFocusRequest(it)
                    }
                } else {
                    audioManager?.abandonAudioFocus(audioFocusChangeListener)
                }
            }

            mediaSession?.run {
                isActive = false

                setPlaybackState(
                    stateBuilder.setState(
                        PlaybackStateCompat.STATE_STOPPED,
                        PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN,
                        1f
                    ).build()
                )
            }

            currentState = PlaybackStateCompat.STATE_STOPPED
            refreshNotificationAndForegroundStatus(currentState)

            stopSelf()
        }

        override fun onSkipToNext() {
            super.onSkipToNext()

            val nextTrack = musicRepository.nextTrack

            updateMetadataFromTrack(nextTrack)

            refreshNotificationAndForegroundStatus(currentState)
            prepareToPlay(nextTrack.trackUrl)
        }

        override fun onSkipToPrevious() {
            super.onSkipToPrevious()

            val previousTrack = musicRepository.previousTrack

            updateMetadataFromTrack(previousTrack)

            refreshNotificationAndForegroundStatus(currentState)
            prepareToPlay(previousTrack.trackUrl)
        }

        private fun prepareToPlay(url: String) {
            if (url != currentUrl) {
                currentUrl = url

                if (extractorsFactory == null || dataSourceFactory == null)
                    return

                val mediaSource = ProgressiveMediaSource.Factory(
                    dataSourceFactory!!,
                    extractorsFactory!!
                ).createMediaSource(
                    MediaItem.fromUri(Uri.parse(url))
                )

                exoPlayer?.setMediaSource(mediaSource)
                exoPlayer?.prepare()
            }
        }

        private fun updateMetadataFromTrack(track: TrackSharedData) {
            trackDuration = track.duration.toLong()

            metadataBuilder.run {
                putString(
                    MediaMetadataCompat.METADATA_KEY_TITLE,
                    track.trackName
                )

                putString(
                    MediaMetadataCompat.METADATA_KEY_ALBUM,
                    track.albumName
                )

                putString(
                    MediaMetadataCompat.METADATA_KEY_ARTIST,
                    track.authorName
                )

                putLong(
                    MediaMetadataCompat.METADATA_KEY_DURATION,
                    track.duration.toLong()
                )
            }

            localBinder.updateMetadata?.onMetadataUpdate(track)
            mediaSession?.setMetadata(metadataBuilder.build())
        }
    }

    private val audioFocusChangeListener: AudioManager.OnAudioFocusChangeListener =
        AudioManager.OnAudioFocusChangeListener { p0 ->
            when (p0) {
                AudioManager.AUDIOFOCUS_GAIN -> mediaSessionCallback.onPlay()
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> mediaSessionCallback.onPause()
                else -> mediaSessionCallback.onPause()
            }
        }

    private val exoPlayerListener = ExoPlayerListener(mediaSessionCallback, exoPlayer)
    private val notificationChannel by lazy {
        NotificationChannel(
            NOTIFICATION_DEFAULT_CHANNEL_ID,
            "Notification channel name",
            NotificationManagerCompat.IMPORTANCE_DEFAULT
        )
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.d("MyLogs", "in Service onBind ${musicRepository.trackCount}")

        return localBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        MediaButtonReceiver.handleIntent(mediaSession, intent)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        baseContext.appComponent.inject(this)

        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        mediaSession = MediaSessionCompat(this, "PlayerService").apply {
            setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                        or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
            )

            setCallback(mediaSessionCallback)

            val activityIntent = Intent(appContext, MainActivity::class.java)

            setSessionActivity(
                PendingIntent.getActivity(appContext, 0, activityIntent, 0)
            )

            val mediaButtonIntent = Intent(
                Intent.ACTION_MEDIA_BUTTON,
                null,
                appContext,
                MediaButtonReceiver::class.java
            )

            setMediaButtonReceiver(
                PendingIntent.getBroadcast(
                    this@PlaybackService,
                    0,
                    mediaButtonIntent,
                    0
                )
            )

            Log.d("MyLogs", "in Service onCreate ${musicRepository.trackCount}")
        }

        sessionToken = mediaSession?.sessionToken

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(notificationChannel)

            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()

            audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setOnAudioFocusChangeListener(audioFocusChangeListener)
                .setAcceptsDelayedFocusGain(false)
                .setWillPauseWhenDucked(true)
                .setAudioAttributes(audioAttributes)
                .build()

            startForeground(
                NOTIFICATION_ID,
                getNotification(PlaybackStateCompat.STATE_PLAYING)
            )
        }

        exoPlayer = ExoPlayer.Builder(this).build().apply {
            addListener(exoPlayerListener)
        }

        val httpDataSourceFactory = OkHttpDataSource.Factory(OkHttpClient())
            .setUserAgent(
                Util.getUserAgent(this, getString(R.string.app_name))
            )

        dataSourceFactory = CacheDataSource.Factory()
            .setCache(cache)
            .setUpstreamDataSourceFactory(httpDataSourceFactory)
            .setFlags(
                CacheDataSource.FLAG_BLOCK_ON_CACHE
                        or CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR
            )

        extractorsFactory = DefaultExtractorsFactory()
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        Log.d("MyLogs", "in Service onGetRoot ${musicRepository.trackCount}")

        return BrowserRoot(
            MY_MEDIA_ROOT_ID,
            null
        )
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        Log.d("MyLogs", "in Service onLoadChildren")

        val data = (1 until musicRepository.trackCount - 1).map { i ->
            val track = musicRepository.getTrackByIndex(i)

            val description = MediaDescriptionCompat.Builder()
                .setDescription(track.authorName)
                .setTitle(track.trackName)
                .setSubtitle(track.authorName)
                .setIconUri(Uri.parse(track.imageUrl))
                .setMediaId("$i")
                .build()

            MediaBrowserCompat.MediaItem(description, FLAG_PLAYABLE)
        }.toMutableList()

        result.sendResult(data)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyLogs", "in Service onDestroy")

        mediaSession?.release()
        exoPlayer?.release()
    }

    fun refreshNotificationAndForegroundStatus(playbackState: Int) {
        when (playbackState) {
            PlaybackStateCompat.STATE_PLAYING -> {
                startForeground(NOTIFICATION_ID, getNotification(playbackState))
            }

            PlaybackStateCompat.STATE_PAUSED -> {
                NotificationManagerCompat.from(this)
                    .notify(NOTIFICATION_ID, getNotification(playbackState))

                stopForeground(false)
            }

            else -> {
                stopForeground(true)
            }
        }
    }

    private fun getNotification(playbackState: Int): Notification {
        val builder = MediaStyleHelper.from(
            this,
            mediaSession,
            NOTIFICATION_DEFAULT_CHANNEL_ID
        )

        builder.addAction(
            NotificationCompat.Action(
                android.R.drawable.ic_media_previous,
                getString(R.string.previous),
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    this,
                    PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                )
            )
        )

        builder.addAction(
            if (playbackState == PlaybackStateCompat.STATE_PLAYING) {
                NotificationCompat.Action(
                    android.R.drawable.ic_media_pause,
                    getString(R.string.pause),
                    MediaButtonReceiver.buildMediaButtonPendingIntent(
                        this,
                        PlaybackStateCompat.ACTION_PLAY_PAUSE
                    )
                )
            } else {
                NotificationCompat.Action(
                    android.R.drawable.ic_media_play,
                    getString(R.string.play),
                    MediaButtonReceiver.buildMediaButtonPendingIntent(
                        this,
                        PlaybackStateCompat.ACTION_PLAY_PAUSE
                    )
                )
            }
        )

        builder.addAction(
            NotificationCompat.Action(
                android.R.drawable.ic_media_next,
                getString(R.string.next),
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    this,
                    PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                )
            )
        )

        builder.run {
            priority = NotificationCompat.PRIORITY_HIGH
            color =
                baseContext.getColor(com.google.android.material.R.color.design_dark_default_color_primary)

            setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(1)
                    .setShowCancelButton(true)
                    .setCancelButtonIntent(
                        MediaButtonReceiver.buildMediaButtonPendingIntent(
                            this@PlaybackService,
                            PlaybackStateCompat.ACTION_STOP
                        )
                    )
                    .setMediaSession(mediaSession?.sessionToken)
            )

            setShowWhen(false)
            setOnlyAlertOnce(true)
            setSmallIcon(R.drawable.ic_launcher_background)
            setChannelId(NOTIFICATION_DEFAULT_CHANNEL_ID)
        }

        return builder.build()
    }

    fun refreshTrackList(list: List<TrackSharedData>, currentPosition: Int?) {
        musicRepository.refreshTrackList(list, currentPosition)
        Log.d("MyLogs", "in Service ${list.size}, $currentPosition")
        NotificationManagerCompat.from(this)
            .notify(
                NOTIFICATION_ID,
                getNotification(PlaybackStateCompat.STATE_PLAYING)
            )
    }

    inner class PlaybackServiceBinder : Binder() {
        fun getService(): PlaybackService = this@PlaybackService

        var updateMetadata: MainActivity.MetadataUpdateCallback? = null

        val mediaSessionToken: MediaSessionCompat.Token
            get() = requireNotNull(mediaSession?.sessionToken) {
                "MediaSessionToken was null"
            }
    }

    private inner class BecomingNoisyReceiver : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            p1?.let { intent ->
                if (intent.action == AudioManager.ACTION_AUDIO_BECOMING_NOISY) {
                    mediaSessionCallback.onPause()
                }
            }
        }
    }

    companion object {
        private const val MY_MEDIA_ROOT_ID = "media_root_id"
        private const val NOTIFICATION_DEFAULT_CHANNEL_ID = "channel_id"
        private const val NOTIFICATION_ID = 404
    }
}