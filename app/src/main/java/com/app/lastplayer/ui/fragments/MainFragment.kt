package com.app.lastplayer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.lastplayer.R
import com.app.lastplayer.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private var binding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }
}