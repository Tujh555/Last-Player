package com.app.lastplayer.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.lastplayer.appComponent
import com.app.lastplayer.data.MainListData
import com.app.lastplayer.ui.MainDataType
import com.app.lastplayer.databinding.FragmentMainBinding
import com.app.lastplayer.ui.adapters.mainFragment.MainListAdapter
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener
import com.app.lastplayer.ui.viewModels.MainViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {
    private var binding: FragmentMainBinding? = null
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }
    private val mainListAdapter by lazy { MainListAdapter() }

    private val seeMoreClickListener = MainListAdapter.SeeMoreClickListener { code, albums ->
        val action = when (code) {
            MainDataType.ALBUM.ordinal -> {
                MainFragmentDirections.actionMainFragmentToMoreAlbumsFragment(
                    albums.map { (it as MainListData.AlbumItem).item }.toTypedArray()
                )
            }
            else -> throw IllegalArgumentException("Unknown data code")
        }

        findNavController().navigate(action)
    }

    private val albumImageClickListener = ImageClickListener { albumId, imageUrl ->
        Log.d("MyLogs", albumId)

        MainFragmentDirections.actionMainFragmentToAlbumDetailedFragment(albumId, imageUrl).also {
            findNavController().navigate(it)
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initListItems()
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

        mainListAdapter.setImageClickListenerOn(
            MainDataType.ALBUM.ordinal,
            albumImageClickListener
        )
        mainListAdapter.seeMoreClickListener = seeMoreClickListener

        binding?.run {
            mainList.adapter = mainListAdapter
            mainList.layoutManager = LinearLayoutManager(requireContext())
        }

        getListItems()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
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