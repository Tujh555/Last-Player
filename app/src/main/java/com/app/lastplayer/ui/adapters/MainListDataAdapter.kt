package com.app.lastplayer.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.data.MainListData
import com.app.lastplayer.databinding.AlbumListItemBinding
import com.app.lastplayer.databinding.AuthorListItemBinding
import com.app.lastplayer.databinding.FeedListItemBinding
import com.app.lastplayer.databinding.PlaylistListItemBinding
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener
import com.app.lastplayer.ui.adapters.viewHolders.AlbumViewHolder
import com.app.lastplayer.ui.adapters.viewHolders.AuthorViewHolder
import com.app.lastplayer.ui.adapters.viewHolders.FeedViewHolder
import com.app.lastplayer.ui.adapters.viewHolders.PlaylistViewHolder

class MainListDataAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mainDataList = mutableListOf<MainListData>()
    private val types = DataType.values().map { it.ordinal }.toSet()
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
            is MainListData.AlbumItem -> DataType.ALBUM.ordinal
            is MainListData.AuthorItem -> DataType.AUTHOR.ordinal
            is MainListData.FeedItem -> DataType.FEED.ordinal
            is MainListData.PlaylistItem -> DataType.PLAYLIST.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            DataType.ALBUM.ordinal -> {
                val binding = AlbumListItemBinding.inflate(inflater, parent, false)
                AlbumViewHolder(binding)
            }
            DataType.AUTHOR.ordinal -> {
                val binding = AuthorListItemBinding.inflate(inflater, parent, false)
                AuthorViewHolder(binding)
            }
            DataType.FEED.ordinal -> {
                val binding = FeedListItemBinding.inflate(inflater, parent, false)
                FeedViewHolder(binding)
            }
            DataType.PLAYLIST.ordinal -> {
                val binding = PlaylistListItemBinding.inflate(inflater, parent, false)
                PlaylistViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown DataType, onCreateViewHolder")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mainDataList[position]

        when (holder.itemViewType) {
            DataType.ALBUM.ordinal -> {
                (holder as AlbumViewHolder).bind(
                    item as MainListData.AlbumItem,
                    imageClickListeners[holder.itemViewType]
                )
            }
            DataType.AUTHOR.ordinal -> {
                (holder as AuthorViewHolder).bind(item as MainListData.AuthorItem)
            }
            DataType.FEED.ordinal -> {
                (holder as FeedViewHolder).bind(item as MainListData.FeedItem)
            }
            DataType.PLAYLIST.ordinal -> {
                (holder as PlaylistViewHolder).bind(item as MainListData.PlaylistItem)
            }
        }
    }

    override fun getItemCount() = mainDataList.size

    enum class DataType {
        ALBUM, AUTHOR, FEED, PLAYLIST
    }
}