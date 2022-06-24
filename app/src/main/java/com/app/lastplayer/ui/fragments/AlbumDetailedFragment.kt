package com.app.lastplayer.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.app.lastplayer.R
import com.app.lastplayer.appComponent
import com.app.lastplayer.databinding.FragmentDetailedAlbumBinding
import com.app.lastplayer.ui.viewModels.AlbumDetailedViewModel
import javax.inject.Inject

class AlbumDetailedFragment : Fragment(R.layout.fragment_detailed_album) {
    private var binding: FragmentDetailedAlbumBinding? = null
    private val viewModel by viewModels<AlbumDetailedViewModel> {
        viewModelFactory
    }
    private val navArgs by navArgs<AlbumDetailedFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTracks(navArgs.albumId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedAlbumBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}