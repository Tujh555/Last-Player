package com.app.lastplayer.ui.fragments.detailed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.lastplayer.Constants.IS_FLIPPED_KEY
import com.app.lastplayer.R
import com.app.lastplayer.ServiceConnector
import com.app.lastplayer.appComponent
import com.app.lastplayer.databinding.FragmentDetailedAlbumBinding
import com.app.lastplayer.ui.adapters.TrackAdapter
import com.app.lastplayer.ui.adapters.clickListeners.TrackClickListener
import com.app.lastplayer.ui.viewModels.detailed.AlbumDetailedViewModel
import com.bumptech.glide.RequestManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlbumDetailedFragment : Fragment(R.layout.fragment_detailed_album) {
    private var binding: FragmentDetailedAlbumBinding? = null
    private var isFlipped = false
    private val viewModel by viewModels<AlbumDetailedViewModel> {
        viewModelFactory
    }
    private val navArgs by navArgs<AlbumDetailedFragmentArgs>()
    private val trackClickListener = TrackClickListener { data, position ->
        val connector = requireActivity() as ServiceConnector

        connector.onClickTrack(data, position)
    }

    @Inject
    lateinit var trackAdapter: TrackAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var glideRequestManager: RequestManager


    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState?.getBoolean(IS_FLIPPED_KEY) != true) {
            viewModel.getTracksFromAlbum(navArgs.albumId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedAlbumBinding.inflate(inflater, container, false)
        getTracks()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trackAdapter.trackClickListener = trackClickListener

        binding?.run {
            glideRequestManager.load(navArgs.albumImage)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)  //TODO make placeholder
                .into(albumImage)

            albumTracksList.layoutManager = LinearLayoutManager(requireContext())
            albumTracksList.adapter = trackAdapter
        }
    }

    override fun onStop() {
        super.onStop()

        isFlipped = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(IS_FLIPPED_KEY, isFlipped)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun getTracks() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.trackList.collect { tracks ->
                    trackAdapter.setList(tracks)
                }
            }
        }
    }
}