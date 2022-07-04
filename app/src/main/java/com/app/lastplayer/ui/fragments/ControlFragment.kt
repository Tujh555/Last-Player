package com.app.lastplayer.ui.fragments

import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.app.lastplayer.MainActivity
import com.app.lastplayer.R
import com.app.lastplayer.ServiceConnector
import com.app.lastplayer.databinding.FragmentControlBinding

class ControlFragment : Fragment() {
    private var binding: FragmentControlBinding? = null
    private var serviceConnector: ServiceConnector? = null
    private var isPlaying = true
    private val playbackStateChangedCallback = MainActivity.MediaCallback { state ->
        state?.let {
            if (it.state == PlaybackStateCompat.STATE_STOPPED) {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .remove(this)
                    .commit()
            }

            isPlaying = it.state == PlaybackStateCompat.STATE_PLAYING

            Log.d("MyLogs", "ControlFragment isPlaying = $isPlaying")

            binding?.run {
                playButton.setImageDrawable(
                    requireContext().getDrawable(
                        if (isPlaying) {
                            R.drawable.button_pause_foreground
                        } else {
                            R.drawable.button_play_foreground
                        }
                    )
                )
                Log.d("MyLogs", "ControlFragment isPlaying = ${playButton.isEnabled}")
            }
        }
    }

    private val updateMetadataCallback = MainActivity.MetadataUpdateCallback {
        binding?.run {
            trackName.text = it.trackName
            authorName.text = it.authorName
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        serviceConnector = requireActivity() as ServiceConnector

        serviceConnector?.playbackStateChangedCallback = this.playbackStateChangedCallback
        serviceConnector?.updateMetadataCallback = this.updateMetadataCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentControlBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onResume() {
        super.onResume()

        binding?.run {
            playButton.setOnClickListener {
                serviceConnector?.mediaController?.transportControls?.let { controls ->
                    if (isPlaying) {
                        controls.pause()
                    } else {
                        controls.play()
                    }
                }
            }

            nextButton.setOnClickListener {
                serviceConnector?.mediaController?.transportControls?.skipToNext()
            }

            previousButton.setOnClickListener {
                serviceConnector?.mediaController?.transportControls?.skipToPrevious()
            }

            serviceConnector?.mediaController?.metadata?.let { data ->
                trackName.text = data.getString(MediaMetadataCompat.METADATA_KEY_TITLE)
                authorName.text = data.getString(MediaMetadataCompat.METADATA_KEY_ARTIST)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance() = ControlFragment()
    }
}