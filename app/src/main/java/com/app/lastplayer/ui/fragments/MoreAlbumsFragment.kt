package com.app.lastplayer.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.R
import com.app.lastplayer.appComponent
import com.app.lastplayer.databinding.FragmentMoreAlbumsBinding
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener
import com.app.lastplayer.ui.adapters.moreAlbumsFragment.AlbumsListAdapter
import com.app.lastplayer.ui.viewModels.MoreAlbumsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoreAlbumsFragment : Fragment(R.layout.fragment_more_albums) {
    private var binding: FragmentMoreAlbumsBinding? = null
    private val adapter by lazy { AlbumsListAdapter() }
    private val navArgs by navArgs<MoreAlbumsFragmentArgs>()

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<MoreAlbumsViewModel> {
        factory
    }

    private val albumImageClickListener = ImageClickListener { itemId, imageUrl ->
        MoreAlbumsFragmentDirections
            .actionMoreAlbumsFragmentToAlbumDetailedFragment(itemId, imageUrl)
            .let { directions ->
                findNavController().navigate(directions)
            }
    }

    private val albumsScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            binding?.run {
                viewModel.isAlbumsListScrolling =
                    newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            recyclerView.layoutManager?.run {
                val scrollOutItems = (this as LinearLayoutManager).findFirstVisibleItemPosition()
                val isEnd = childCount + scrollOutItems == itemCount

                if (viewModel.isAlbumsListScrolling && isEnd) {
                    viewModel.run {
                        isAlbumsListScrolling = false
                        getAlbums()
                    }

                    changeProgressBarState()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAlbums()

        adapter.run {
            imageClickListener = albumImageClickListener
            addToList(navArgs.albumsList.toList())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreAlbumsBinding.inflate(inflater, container, false)
        getAlbums()

        binding?.progressBar?.visibility = View.GONE
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            albumsList.adapter = adapter
            albumsList.layoutManager = LinearLayoutManager(requireContext())
            albumsList.addOnScrollListener(albumsScrollListener)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun getAlbums() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.albumsList.collect {
                    adapter.addToList(it)

                    changeProgressBarState()
                }
            }
        }
    }

    private fun changeProgressBarState() {
        binding?.run {
            lifecycleScope.launch {
                delay(800L)

                progressBar.visibility = if (progressBar.visibility == View.GONE) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }
    }
}

// OnScrollListener,