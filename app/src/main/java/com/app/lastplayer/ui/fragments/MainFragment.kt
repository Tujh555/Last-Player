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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.whenResumed
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.lastplayer.R
import com.app.lastplayer.appComponent
import com.app.lastplayer.data.MainListData
import com.app.lastplayer.data.MainListItem
import com.app.lastplayer.databinding.FragmentMainBinding
import com.app.lastplayer.di.modules.viewModels.ViewModelFactory
import com.app.lastplayer.ui.adapters.MainListAdapter
import com.app.lastplayer.ui.viewModels.MainViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {
    private var binding: FragmentMainBinding? = null
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }
    private val mainListAdapter by lazy { MainListAdapter() }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().appComponent.inject(this)
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