package com.app.lastplayer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.lastplayer.databinding.FragmentLoginBinding
import com.app.lastplayer.requireText
import com.google.firebase.auth.FirebaseAuth
import kotlin.IllegalStateException

class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    private val auth = FirebaseAuth.getInstance()
    private var user = auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            registerButton.setOnClickListener {
                try {
                    auth.createUserWithEmailAndPassword(
                        emailEditText.requireText(),
                        passwordEdiText.requireText()
                    ).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Signup successful",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (user != null) {
                            Toast.makeText(
                                requireContext(),
                                "You are already registered.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Something went wrong... \nPlease, reenter.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: IllegalStateException) {
                    Toast.makeText(
                        requireContext(),
                        "Something went wrong... \nPlease, reenter.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            loginButton.setOnClickListener {
                try {
                    auth.signInWithEmailAndPassword(
                        emailEditText.requireText(),
                        passwordEdiText.requireText()
                    ).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "SignIn successful",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Something went wrong... \nPlease, reenter.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: IllegalStateException) {
                    Toast.makeText(
                        requireContext(),
                        "Something went wrong... \nPlease, reenter.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }
}