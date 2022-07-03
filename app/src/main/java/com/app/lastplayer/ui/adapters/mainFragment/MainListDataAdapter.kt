package com.app.lastplayer.ui.adapters.mainFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.data.MainListData
import com.app.lastplayer.databinding.AlbumListItemBinding
import com.app.lastplayer.databinding.AuthorListItemBinding
import com.app.lastplayer.databinding.FeedListItemBinding
import com.app.lastplayer.databinding.PlaylistListItemBinding
import com.app.lastplayer.ui.MainDataType
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener
import com.app.lastplayer.ui.adapters.viewHolders.AlbumViewHolder
import com.app.lastplayer.ui.adapters.viewHolders.AuthorViewHolder
import com.app.lastplayer.ui.adapters.viewHolders.FeedViewHolder
import com.app.lastplayer.ui.adapters.viewHolders.PlaylistViewHolder

class MainListDataAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mainDataList = mutableListOf<MainListData>()
    private val types = MainDataType.values().map { it.ordinal }.toSet()
    private val imageClickListeners = mutableMapOf<Int, ImageClickListener>()

    fun setList(list: List<MainListData>) {
        mainDataList.clear()
        mainDataList.addAll(list)
        notifyDataSetChanged()
    }

    fun setClickListeners(map: Map<Int, ImageClickListener>) {
        map.forEach { (key, value) ->
            if (!types.contains(key)) {
                throw IllegalArgumentException("Unknown Data Type")
            }

            imageClickListeners[key] = value
        }
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
                    imageClickListeners[holder.itemViewType]
                )
            }
            MainDataType.AUTHOR.ordinal -> {
                (holder as AuthorViewHolder).bind(
                    item as MainListData.AuthorItem,
                    imageClickListeners[holder.itemViewType]
                )
            }
            MainDataType.FEED.ordinal -> {
                (holder as FeedViewHolder).bind(
                    item as MainListData.FeedItem,
                    imageClickListeners[holder.itemViewType]
                )
            }
            MainDataType.PLAYLIST.ordinal -> {
                (holder as PlaylistViewHolder).bind(
                    item as MainListData.PlaylistItem,
                    imageClickListeners[holder.itemViewType]
                )
            }
        }
    }

    override fun getItemCount() = mainDataList.size
}