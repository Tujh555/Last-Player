package com.app.lastplayer.ui.adapters.more.morePlaylistsFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.app.lastplayer.data.MainListData
import com.app.lastplayer.data.remote.Playlist
import com.app.lastplayer.databinding.PlaylistListItemBinding
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener
import com.app.lastplayer.ui.adapters.viewHolders.PlaylistViewHolder

private val PLAYLISTS_COMPARATOR = object : DiffUtil.ItemCallback<Playlist>() {
    override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
        return oldItem == newItem
    }
}

class PlaylistsListAdapter : PagingDataAdapter<Playlist, PlaylistViewHolder>(PLAYLISTS_COMPARATOR) {
    var imageClickListener: ImageClickListener? = null

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(
                MainListData.PlaylistItem(item),
                imageClickListener
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return PlaylistViewHolder(
            PlaylistListItemBinding.inflate(inflater, parent, false).apply {
                root.layoutParams = ViewGroup.LayoutParams(
                    parent.layoutParams.width,
                    root.layoutParams.height
                )
            }
        )
    }
}