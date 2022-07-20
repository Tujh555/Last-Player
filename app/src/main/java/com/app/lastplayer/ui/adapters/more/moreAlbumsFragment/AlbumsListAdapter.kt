package com.app.lastplayer.ui.adapters.more.moreAlbumsFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.app.lastplayer.data.MainListData
import com.app.lastplayer.data.remote.Album
import com.app.lastplayer.databinding.AlbumListItemBinding
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener
import com.app.lastplayer.ui.adapters.viewHolders.AlbumViewHolder

private val ALBUM_COMPARATOR = object : DiffUtil.ItemCallback<Album>() {
    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean =
        oldItem.id == newItem.id
}

class AlbumsListAdapter : PagingDataAdapter<Album, AlbumViewHolder>(ALBUM_COMPARATOR) {
    var imageClickListener: ImageClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return AlbumViewHolder(
            AlbumListItemBinding.inflate(inflater, parent, false).apply {
                root.layoutParams = ViewGroup.LayoutParams(
                    parent.layoutParams.width,
                    root.layoutParams.height
                )
            }
        )
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        getItem(position)?.let { album ->
            holder.bind(
                MainListData.AlbumItem(album),
                imageClickListener
            )
        }
    }
}


