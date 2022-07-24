package com.app.lastplayer.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.lastplayer.R
import com.app.lastplayer.appComponent
import com.app.lastplayer.databinding.FragmentLoginBinding
import com.app.lastplayer.requireText
import com.app.lastplayer.ui.viewModels.LoginFragmentViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.IllegalStateException

class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    private val viewModel by viewModels<LoginFragmentViewModel> {
        factory
    }

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

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
                            viewModel.insertUser(
                                auth.currentUser?.uid!!
                            )

                            goToNextFragment()
                            showToast("Signup successful")
                        } else if (task.exception is FirebaseAuthUserCollisionException) {
                            showToast("You are already registered.")
                        } else {
                            showToast("Something went wrong... \nPlease, reenter.")
                        }
                    }
                } catch (e: IllegalStateException) {
                    showToast("Something went wrong... \nPlease, reenter.")
                }
            }

            loginButton.setOnClickListener {
                try {
                    auth.signInWithEmailAndPassword(
                        emailEditText.requireText(),
                        passwordEdiText.requireText()
                    ).addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            viewModel.insertUser(
                                auth.currentUser?.uid!!
                            )
                            goToNextFragment()
                            showToast("SignIn successful")
                        } else {
                            showToast("Wrong password")
                        }
                    }
                } catch (e: IllegalStateException) {
                    showToast("Something went wrong... \nPlease, reenter.")
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_LONG
        )
        Log.d("MyLogs", message)
    }

    private fun goToNextFragment() {
        lifecycleScope.launch {
            delay(500L)
            R.id.action_global_mainFragment.also {
                findNavController().navigate(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }
}