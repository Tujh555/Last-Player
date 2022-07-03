package com.app.lastplayer.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.lastplayer.appComponent
import com.app.lastplayer.databinding.FragmentAccountBinding
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AccountFragment : Fragment() {
    private var binding: FragmentAccountBinding? = null

    @Inject
    lateinit var auth: FirebaseAuth

    private val loginButtonClickListener = View.OnClickListener {
        AccountFragmentDirections.actionAccountFragmentToLoginFragment().also {
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
        binding = FragmentAccountBinding.inflate(inflater, container, false)

        val user = auth.currentUser

        binding?.run {
            if (user == null) {
                unregisteredUserLayout.visibility = View.VISIBLE
                registeredUserLayout.visibility = View.GONE
            } else {
                unregisteredUserLayout.visibility = View.GONE
                registeredUserLayout.visibility = View.VISIBLE
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