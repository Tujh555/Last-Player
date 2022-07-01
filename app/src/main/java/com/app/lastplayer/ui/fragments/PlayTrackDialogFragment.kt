package com.app.lastplayer.ui.fragments

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.app.lastplayer.R
import com.app.lastplayer.appComponent
import com.app.lastplayer.databinding.PlayBottomSheetBinding
import com.app.lastplayer.media.PlaybackService
import com.app.lastplayer.toTrackDuration
import com.bumptech.glide.RequestManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class PlayTrackDialogFragment : BottomSheetDialogFragment() {
    private val args by navArgs<PlayTrackDialogFragmentArgs>()

    private val mediaControllerCallback = object : MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)

            state?.let {
                val isPlaying = it.state == PlaybackStateCompat.STATE_PLAYING
                Log.d("MyLogs", "play button isEnabled = $isPlaying")

                binding?.run {
                    playButton.isEnabled = isPlaying
                }
            }
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            playbackBinder = p1 as PlaybackService.PlaybackServiceBinder

            try {
                playbackBinder?.let { binder ->
                    mediaController = MediaControllerCompat(
                        requireContext(),
                        binder.mediaSessionToken
                    )

                    mediaController?.registerCallback(mediaControllerCallback)

                    mediaControllerCallback.onPlaybackStateChanged(
                        mediaController?.playbackState
                    )

                    playbackService = playbackBinder?.getService() as PlaybackService

                    playbackService?.run {
                        refreshTrackList(args.tracksData.toList(), args.position)
                    }

                    Log.d("MyLogs", "in serviceConnection $binder")
                }
            } catch (e: RemoteException) {
                mediaController = null
            }
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            playbackBinder = null
            Log.d("MyLogs", "onServiceDisconnected")

            if (mediaController != null) {
                mediaController?.unregisterCallback(mediaControllerCallback)
                mediaController = null
            }
        }

    }

    @Inject
    lateinit var glideRequestManager: RequestManager

    private var binding: PlayBottomSheetBinding? = null
    private var playbackBinder: PlaybackService.PlaybackServiceBinder? = null
        get() = requireNotNull(field)
    private var mediaController: MediaControllerCompat? = null
    private var playbackService: PlaybackService? = null

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().bindService(
            Intent(requireContext(), PlaybackService::class.java),
            serviceConnection,
            BIND_AUTO_CREATE
        )

        Log.d("MyLogs", "in Fragment OnCreate ${args.tracksData.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PlayBottomSheetBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val trackArgs = args.tracksData[args.position]

        binding?.run {
            glideRequestManager.load(trackArgs.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(trackImage)

            trackName.text = trackArgs.trackName

            authorName.text = trackArgs.authorName

            elapsedTime.text = "0:00"
            trackDuration.text = trackArgs.duration.toTrackDuration()

            playButton.setOnClickListener {
                mediaController?.let { controller ->
                    if (playButton.isEnabled) {
                        Log.d("MyLogs", "PlayButtonListener ${playButton.isEnabled}")
                        controller.transportControls.pause()
                    } else {
                        controller.transportControls.play()
                    }
                }
            }

            nextButton.setOnClickListener {
                mediaController?.transportControls?.skipToNext()
            }

            previousButton.setOnClickListener {
                mediaController?.transportControls?.skipToPrevious()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        playbackBinder = null

        if (mediaController != null) {
            mediaController?.unregisterCallback(mediaControllerCallback)
            mediaController = null
        }

        playbackService?.let { requireActivity().unbindService(serviceConnection)}
        binding = null
    }
}