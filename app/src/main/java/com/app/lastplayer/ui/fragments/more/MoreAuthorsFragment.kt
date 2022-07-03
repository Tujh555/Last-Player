package com.app.lastplayer.ui.fragments.more

import android.content.Context
import android.os.Bundle
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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.lastplayer.R
import com.app.lastplayer.appComponent
import com.app.lastplayer.databinding.FragmentMoreAuthorsBinding
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener
import com.app.lastplayer.ui.adapters.more.moreAuthorsFragment.AuthorsListAdapter
import com.app.lastplayer.ui.viewModels.more.MoreAuthorsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoreAuthorsFragment : Fragment() {
    private var binding: FragmentMoreAuthorsBinding? = null
    private val adapter by lazy { AuthorsListAdapter() }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<MoreAuthorsViewModel> {
        factory
    }

    private val authorImageClickListener = ImageClickListener { itemId, imageUrl ->
        MoreAuthorsFragmentDirections.actionMoreAuthorsFragmentToAuthorDetailedFragment(
            authorId = itemId,
            authorImage = imageUrl
        ).also {
            findNavController().navigate(it)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter.imageClickListener = authorImageClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreAuthorsBinding.inflate(inflater, container, false)
        getAuthors()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            authorsList.adapter = adapter
            authorsList.layoutManager = LinearLayoutManager(requireContext())
            authorsList.addItemDecoration(
                DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL).apply {
                    requireContext().getDrawable(R.drawable.divider)?.let {
                        setDrawable(it)
                    }
                }
            )
        }
    }

    private fun getAuthors() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.authorsList.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
    }
}