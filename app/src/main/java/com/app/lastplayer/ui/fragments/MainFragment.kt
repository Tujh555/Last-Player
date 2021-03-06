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
import com.app.lastplayer.di.clickListenersComponent.ClickListenersComponent
import com.app.lastplayer.di.clickListenersComponent.DaggerClickListenersComponent
import com.app.lastplayer.ui.MainDataType
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener
import com.app.lastplayer.ui.adapters.mainFragment.MainListAdapter
import com.app.lastplayer.ui.viewModels.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {
    private var binding: FragmentMainBinding? = null
    private var isFlipped = false
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }
    private val mainListAdapter by lazy { MainListAdapter() }
    private var clickListenersComponent: ClickListenersComponent? = null

    private val seeMoreClickListener = MainListAdapter.SeeMoreClickListener { code, _ ->
         when (code) {
            MainDataType.ALBUM.ordinal -> {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToMoreAlbumsFragment()
                )
            }

            MainDataType.AUTHOR.ordinal -> {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToMoreAuthorsFragment()
                )
            }

            MainDataType.PLAYLIST.ordinal -> {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToMorePlaylistsFragment()
                )
            }
        }
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

    private val initCallback = MainViewModel.InitialCallback {
        binding?.progressBar?.visibility = View.GONE
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState?.getBoolean(IS_FLIPPED_KEY) != true) {
            viewModel.initialCallback = initCallback
            viewModel.initListItems()
        }

        clickListenersComponent = DaggerClickListenersComponent.builder()
            .albumClickListener(albumImageClickListener)
            .authorClickListener(authorImageClickListener)
            .playlistClickListener(playlistImageClickListener)
            .feedClickListener(feedImageClickListener)
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainListAdapter.seeMoreClickListener = seeMoreClickListener
        mainListAdapter.clickListenerComponent = clickListenersComponent

        binding?.run {
            mainList.adapter = mainListAdapter
            mainList.layoutManager = LinearLayoutManager(requireContext())
        }

        getListItems()
    }

    override fun onStop() {
        super.onStop()

        isFlipped = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("MyLogs", "onSaveInstanceState")

        outState.putBoolean(IS_FLIPPED_KEY, isFlipped)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }

    private fun getListItems() {
        lifecycleScope.launch {
            Log.d("LifeCycle", "GetListItems")
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                Log.d("LifeCycle", "RepeatOnLifecycle")
                viewModel.mainListItems.collect { items ->
                    Log.d("LifeCycle", "Collect")
                    mainListAdapter.setList(items)
                }
            }
        }
    }
}