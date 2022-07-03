package com.app.lastplayer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.app.lastplayer.databinding.DialogFragmentChangeUserDataBinding

class ChangeUserDataDialogFragment : DialogFragment() {
    private var binding: DialogFragmentChangeUserDataBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentChangeUserDataBinding.inflate(inflater, container, false)

        return binding?.root
    }
}