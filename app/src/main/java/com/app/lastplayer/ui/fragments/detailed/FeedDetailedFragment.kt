package com.app.lastplayer.ui.fragments.detailed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.app.lastplayer.R
import com.app.lastplayer.appComponent
import com.app.lastplayer.databinding.FragmentDetailedFeedBinding
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class FeedDetailedFragment : Fragment() {
    private var binding: FragmentDetailedFeedBinding? = null
    private val args by navArgs<FeedDetailedFragmentArgs>()

    @Inject
    lateinit var glideRequestManager: RequestManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedFeedBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            glideRequestManager.load(args.feedImage)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_feed_24)
                .into(feedImage)

            feedText.text = args.feedText
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }
}