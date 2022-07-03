package com.app.lastplayer.ui.fragments.more

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.R
import com.app.lastplayer.appComponent
import com.app.lastplayer.databinding.FragmentMoreAlbumsBinding
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener
import com.app.lastplayer.ui.adapters.more.moreAlbumsFragment.AlbumsListAdapter
import com.app.lastplayer.ui.viewModels.more.MoreAlbumsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

class MoreAlbumsFragment : Fragment(R.layout.fragment_more_albums) {
    private var binding: FragmentMoreAlbumsBinding? = null
    private val adapter by lazy { AlbumsListAdapter() }

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter.imageClickListener = albumImageClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreAlbumsBinding.inflate(inflater, container, false)

        getAlbums()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            albumsList.adapter = adapter
            albumsList.layoutManager = LinearLayoutManager(requireContext())
            albumsList.addItemDecoration(
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

    private fun getAlbums() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.albumsList.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
    }
}