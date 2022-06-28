package com.app.lastplayer.ui.adapters.mainFragment.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.R
import com.app.lastplayer.appComponent
import com.app.lastplayer.data.MainListData
import com.app.lastplayer.databinding.FeedListItemBinding

class FeedViewHolder(
    private val binding: FeedListItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    private val glideRequestManager by lazy { itemView.context.appComponent.glideRequestManager }

    fun bind(_feed: MainListData.FeedItem) {
        val feed = _feed.item

        binding.run {
            feedType.text = feed.type

            feed.title?.let { title ->
                when {
                    title.russian.isNotBlank() -> feedTitle.text = title.russian
                    title.english.isNotBlank() -> feedTitle.text = title.english
                    else -> feedTitle.text = "empty"
                }
            }

            glideRequestManager.load(feed.images?.size315_111)
                .placeholder(R.drawable.ic_launcher_background)  // TODO(Make placeholder)
                .centerCrop()
                .into(feedImage)

            feed.text?.let { text ->
                when {
                    text.russian.isNotBlank() -> feedText.text = text.russian
                    text.english.isNotBlank() -> feedText.text = text.english
                    else -> feedText.text = "empty"
                }
            }
        }
    }
}