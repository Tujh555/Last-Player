package com.app.lastplayer.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.lastplayer.Constants.IS_FLIPPED_KEY
import com.app.lastplayer.appComponent
import com.app.lastplayer.databinding.FragmentMainBinding
import com.app.lastplayer.ui.MainDataType
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener
import com.app.lastplayer.ui.adapters.mainFragment.MainListAdapter
import com.app.lastplayer.ui.viewModels.MainViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {
    private var binding: FragmentMainBinding? = null
    private var isFlipped = false
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }
    private val mainListAdapter by lazy { MainListAdapter() }

    private val seeMoreClickListener = MainListAdapter.SeeMoreClickListener { code, _ ->
        val action = when (code) {
            MainDataType.ALBUM.ordinal -> {
                MainFragmentDirections.actionMainFragmentToMoreAlbumsFragment()
            }

            MainDataType.AUTHOR.ordinal -> {
                MainFragmentDirections.actionMainFragmentToMoreAuthorsFragment()
            }

            MainDataType.PLAYLIST.ordinal -> {
                MainFragmentDirections.actionMainFragmentToMorePlaylistsFragment()
            }

            MainDataType.FEED.ordinal -> {
                MainFragmentDirections.actionMainFragmentToMoreFeedsFragment()
            }

            else -> throw IllegalArgumentException("Unknown data code")
        }

        findNavController().navigate(action)
    }

    private val albumImageClickListener = ImageClickListener { albumId, imageUrl ->
        MainFragmentDirections.actionMainFragmentToAlbumDetailedFragment(albumId, imageUrl).also {
            findNavController().navigate(it)
        }
    }

    private val authorImageClickListener = ImageClickListener { authorId, imageUrl ->
        MainFragmentDirections.actionMainFragmentToAuthorDetailedFragment(authorId, imageUrl).also {
            findNavController().navigate(it)
        }
    }

    private val playlistImageClickListener = ImageClickListener { playlistId, imageUrl ->
        MainFragmentDirections.actionMainFragmentToPlaylistDetailedFragment(
            playlistId = playlistId,
            imageUrl = imageUrl,
        ).also {
            findNavController().navigate(it)
        }
    }

    private val feedImageClickListener = ImageClickListener { feedText, imageUrl ->
        MainFragmentDirections.actionMainFragmentToFeedDetailedFragment(
            feedText = feedText,
            feedImage = imageUrl
        ).also {
            findNavController().navigate(it )
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("MyLogs", " onAttach")
        context.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MyLogs", "onCreate")
        if (savedInstanceState?.getBoolean(IS_FLIPPED_KEY) != true) {
            viewModel.initListItems()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MyLogs", "onCreateView")
        binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MyLogs", "onViewCreated")

        mainListAdapter.setImageClickListenerOn(
            MainDataType.ALBUM.ordinal,
            albumImageClickListener
        )

        mainListAdapter.setImageClickListenerOn(
            MainDataType.AUTHOR.ordinal,
            authorImageClickListener
        )

        mainListAdapter.setImageClickListenerOn(
            MainDataType.PLAYLIST.ordinal,
            playlistImageClickListener
        )

        mainListAdapter.setImageClickListenerOn(
            MainDataType.FEED.ordinal,
            feedImageClickListener
        )

        mainListAdapter.seeMoreClickListener = seeMoreClickListener

        binding?.run {
            mainList.adapter = mainListAdapter
            mainList.layoutManager = LinearLayoutManager(requireContext())
        }

        getListItems()
    }

    override fun onStart() {
        super.onStart()
        Log.d("MyLogs", "onStart")
    }

    override fun onResume() {
        super.onResume()

        Log.d("MyLogs", "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MyLogs", "onStop")
        isFlipped = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("MyLogs", "onSaveInstanceState")

        outState.putBoolean(IS_FLIPPED_KEY, isFlipped)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d("MyLogs", "onDestroyView")
        binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyLogs", "onDestroy")
    }

    private fun getListItems() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.mainListItems.collect { items ->
                    mainListAdapter.setList(items)
                }
            }
        }
    }
}