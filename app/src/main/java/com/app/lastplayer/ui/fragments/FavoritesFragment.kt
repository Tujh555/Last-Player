package com.app.lastplayer.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.lastplayer.appComponent
import com.app.lastplayer.databinding.FragmentAccountBinding
import com.app.lastplayer.databinding.FragmentFavoritesBinding
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FavoritesFragment : Fragment() {
    private var binding: FragmentFavoritesBinding? = null

    @Inject
    lateinit var auth: FirebaseAuth

    private val loginButtonClickListener = View.OnClickListener {
        FavoritesFragmentDirections.actionFavoritesFragmentToLoginFragment().also {
            findNavController().navigate(it)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        val user = auth.currentUser

        binding?.run {
            if (user == null) {
                registeredUserLayout.visibility = View.GONE
                unregisteredUserLayout.visibility = View.VISIBLE
            } else {
                registeredUserLayout.visibility = View.VISIBLE
                unregisteredUserLayout.visibility = View.GONE
            }
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            loginButton.setOnClickListener(loginButtonClickListener)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }
}