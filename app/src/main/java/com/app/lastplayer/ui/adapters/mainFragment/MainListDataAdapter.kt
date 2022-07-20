package com.app.lastplayer.ui.adapters.mainFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.data.MainListData
import com.app.lastplayer.databinding.AlbumListItemBinding
import com.app.lastplayer.databinding.AuthorListItemBinding
import com.app.lastplayer.databinding.FeedListItemBinding
import com.app.lastplayer.databinding.PlaylistListItemBinding
import com.app.lastplayer.di.clickListenersComponent.AlbumClickListener
import com.app.lastplayer.di.clickListenersComponent.AuthorClickListener
import com.app.lastplayer.di.clickListenersComponent.FeedCliclListener
import com.app.lastplayer.di.clickListenersComponent.PlaylistClickListener
import com.app.lastplayer.ui.MainDataType
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener
import com.app.lastplayer.ui.adapters.viewHolders.AlbumViewHolder
import com.app.lastplayer.ui.adapters.viewHolders.AuthorViewHolder
import com.app.lastplayer.ui.adapters.viewHolders.FeedViewHolder
import com.app.lastplayer.ui.adapters.viewHolders.PlaylistViewHolder
import javax.inject.Inject

class MainListDataAdapter(
    private val albumClickListener: ImageClickListener?,
    private val authorClickListener: ImageClickListener?,
    private val playlistClickListener: ImageClickListener?,
    private val feedClickListener: ImageClickListener?,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mainDataList = mutableListOf<MainListData>()

    fun setList(list: List<MainListData>) {
        mainDataList.clear()
        mainDataList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {

        return when (mainDataList[position]) {
            is MainListData.AlbumItem -> MainDataType.ALBUM.ordinal
            is MainListData.AuthorItem -> MainDataType.AUTHOR.ordinal
            is MainListData.FeedItem -> MainDataType.FEED.ordinal
            is MainListData.PlaylistItem -> MainDataType.PLAYLIST.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            MainDataType.ALBUM.ordinal -> {
                val binding = AlbumListItemBinding.inflate(inflater, parent, false)
                AlbumViewHolder(binding)
            }
            MainDataType.AUTHOR.ordinal -> {
                val binding = AuthorListItemBinding.inflate(inflater, parent, false)
                AuthorViewHolder(binding)
            }
            MainDataType.FEED.ordinal -> {
                val binding = FeedListItemBinding.inflate(inflater, parent, false)
                FeedViewHolder(binding)
            }
            MainDataType.PLAYLIST.ordinal -> {
                val binding = PlaylistListItemBinding.inflate(inflater, parent, false)
                PlaylistViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown MainDataType, onCreateViewHolder")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mainDataList[position]

        when (holder.itemViewType) {
            MainDataType.ALBUM.ordinal -> {
                (holder as AlbumViewHolder).bind(
                    item as MainListData.AlbumItem,
                    albumClickListener
                )
            }
            MainDataType.AUTHOR.ordinal -> {
                (holder as AuthorViewHolder).bind(
                    item as MainListData.AuthorItem,
                    authorClickListener
                )
            }
            MainDataType.FEED.ordinal -> {
                (holder as FeedViewHolder).bind(
                    item as MainListData.FeedItem,
                    feedClickListener
                )
            }
            MainDataType.PLAYLIST.ordinal -> {
                (holder as PlaylistViewHolder).bind(
                    item as MainListData.PlaylistItem,
                    playlistClickListener
                )
            }
        }
    }

    override fun getItemCount() = mainDataList.size
}