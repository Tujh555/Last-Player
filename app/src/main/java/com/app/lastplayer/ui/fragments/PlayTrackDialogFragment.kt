package com.app.lastplayer.ui.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.navArgs
import com.app.lastplayer.R
import com.app.lastplayer.appComponent
import com.app.lastplayer.data.TrackSharedData
import com.app.lastplayer.databinding.PlayBottomSheetBinding
import com.app.lastplayer.toTrackDuration
import com.bumptech.glide.RequestManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class PlayTrackDialogFragment : BottomSheetDialogFragment() {
    private var binding: PlayBottomSheetBinding? = null
    private val args by navArgs<PlayTrackDialogFragmentArgs>()
    private val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            Log.d("MyLogs", "SlideState = $newState")
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            Log.d("MyLogs", "OnSlide offset = $slideOffset")

            binding?.run {
                when {
                    slideOffset > 0 -> {
                        layoutCollapsed.alpha = 1 - 1.85f * slideOffset
                        layoutExpanded.alpha = slideOffset * slideOffset
                    }

                    slideOffset > 0.6 -> {
                        layoutCollapsed.visibility = View.GONE
                        layoutExpanded.visibility = View.VISIBLE
                    }

                    slideOffset < 0.6 && layoutExpanded.visibility == View.VISIBLE -> {
                        layoutExpanded.visibility = View.GONE
                        layoutCollapsed.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    @Inject
    lateinit var glideRequestManager: RequestManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun getTheme(): Int {
        Log.d("MyLogs", "getTheme()")

        return R.style.AppBottomSheetDialogTheme
    }

    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
        Log.d("MyLogs", "show")
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

        Log.d("MyLogs", args.tracksData.joinToString { it.toString() })

        binding?.run {
            glideRequestManager.load(trackArgs.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(trackImage)

            trackNameCollapsed.text = trackArgs.trackName
            trackName.text = trackArgs.trackName

            authorNameCollapsed.text = trackArgs.authorName
            authorName.text = trackArgs.authorName

            elapsedTime.text = "0:00"
            trackDuration.text = trackArgs.duration.toTrackDuration()
        }

        Log.d("MyLogs", binding?.trackName?.text.toString())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val density = requireContext().resources.displayMetrics.density

        dialog?.let { playDialog ->
            val bottomSheet = playDialog
                .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            Log.d("MyLogs", "onCreateDialog() ${behavior.peekHeight}")
            behavior.addBottomSheetCallback(bottomSheetCallback)
        }

        return super.onCreateDialog(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }

    companion object {
        private const val COLLAPSED_HEIGHT = 60
    }
}