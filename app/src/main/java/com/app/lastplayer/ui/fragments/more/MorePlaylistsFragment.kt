package com.app.lastplayer.ui.fragments.more

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.lastplayer.R
import com.app.lastplayer.appComponent
import com.app.lastplayer.databinding.FragmentMorePlaylistsBinding
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener
import com.app.lastplayer.ui.adapters.more.morePlaylistsFragment.PlaylistsListAdapter
import com.app.lastplayer.ui.viewModels.more.MorePlaylistsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MorePlaylistsFragment : Fragment() {
    private var binding: FragmentMorePlaylistsBinding? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<MorePlaylistsViewModel> {
        factory
    }

    private val adapter by lazy { PlaylistsListAdapter() }

    private val playlistImageClickListener = ImageClickListener { playlistId, imageUrl ->
        MorePlaylistsFragmentDirections.actionMorePlaylistsFragmentToPlaylistDetailedFragment(
            playlistId = playlistId,
            imageUrl = imageUrl
        ).also {
            findNavController().navigate(it)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter.imageClickListener = playlistImageClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMorePlaylistsBinding.inflate(inflater, container, false)
        getPlaylists()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            playlistsList.adapter = adapter
            playlistsList.layoutManager = LinearLayoutManager(requireContext())
            playlistsList.addItemDecoration(
                DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL).apply {
                    requireContext().getDrawable(R.drawable.divider)?.let {
                        setDrawable(it)
                    }
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }

    private fun getPlaylists() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.playlistList.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }
}