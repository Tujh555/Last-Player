package com.app.lastplayer.ui.fragments.detailed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.lastplayer.Constants
import com.app.lastplayer.Constants.IS_FLIPPED_KEY
import com.app.lastplayer.R
import com.app.lastplayer.ServiceConnector
import com.app.lastplayer.appComponent
import com.app.lastplayer.databinding.FragmentDetailedAuthorBinding
import com.app.lastplayer.ui.adapters.TrackAdapter
import com.app.lastplayer.ui.adapters.clickListeners.AddTofavoritesClickListener
import com.app.lastplayer.ui.adapters.clickListeners.TrackClickListener
import com.app.lastplayer.ui.viewModels.detailed.AuthorDetailedViewModel
import com.bumptech.glide.RequestManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorDetailedFragment : Fragment() {
    private var binding: FragmentDetailedAuthorBinding? = null
    private var isFlipped = false
    private val args by navArgs<AuthorDetailedFragmentArgs>()
    private val viewModel by viewModels<AuthorDetailedViewModel> {
        factory
    }

    private val trackClickListener = TrackClickListener { data, position ->
        val connector = requireActivity() as ServiceConnector

        connector.onClickTrack(data, position)
    }

    private val addToFavoritesClickListener = AddTofavoritesClickListener { track ->
        Toast.makeText(
            requireContext(),
            "Added",
            Toast.LENGTH_SHORT
        ).show()

        auth.currentUser?.uid?.let { id ->
            viewModel.insertTrack(track, id)
        }
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var glideRequestManager: RequestManager

    @Inject
    lateinit var trackAdapter: TrackAdapter

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState?.getBoolean(IS_FLIPPED_KEY) != true) {
            viewModel.getTracksFromAuthor(args.authorId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedAuthorBinding.inflate(inflater, container, false)
        getTracks()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trackAdapter.trackClickListener = trackClickListener
        trackAdapter.addTofavoritesClickListener = addToFavoritesClickListener

        binding?.run {
            glideRequestManager.load(args.authorImage)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_person_24)
                .into(authorImage)

            authorTracksList.layoutManager = LinearLayoutManager(requireContext())
            authorTracksList.adapter = trackAdapter
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
                    binding?.authorName?.text = tracks.firstOrNull()?.authorName ?: ""
                    trackAdapter.setList(tracks)
                }
            }
        }
    }
}