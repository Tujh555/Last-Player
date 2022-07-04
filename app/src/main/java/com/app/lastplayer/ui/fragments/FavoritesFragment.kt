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
import com.app.lastplayer.Constants
import com.app.lastplayer.appComponent
import com.app.lastplayer.databinding.FragmentFavoritesBinding
import com.app.lastplayer.ui.adapters.TrackAdapter
import com.app.lastplayer.ui.viewModels.FavoriteViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesFragment : Fragment() {
    private var binding: FragmentFavoritesBinding? = null

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var trackAdapter: TrackAdapter

    private val viewModel by viewModels<FavoriteViewModel> {
        factory
    }

    private var user: FirebaseUser? = null

    private val loginButtonClickListener = View.OnClickListener {
        FavoritesFragmentDirections.actionFavoritesFragmentToLoginFragment().also {
            findNavController().navigate(it)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
        user = auth.currentUser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (user != null) {
            viewModel.getUserTracks(user!!.uid)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        changeUiState()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            loginButton.setOnClickListener(loginButtonClickListener)

            tracksList.adapter = trackAdapter
            tracksList.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }

    private fun changeUiState() {
        binding?.run {
            if (user == null) {
                registeredUserLayout.visibility = View.GONE
                unregisteredUserLayout.visibility = View.VISIBLE
            } else {
                getTracks()
                registeredUserLayout.visibility = View.VISIBLE
                unregisteredUserLayout.visibility = View.GONE
            }
        }
    }

    private fun getTracks() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.tracks.collect {
                    Log.d("MyLogs", "FavoritesFragment $it")
                    trackAdapter.setList(it)
                }
            }
        }
    }
}