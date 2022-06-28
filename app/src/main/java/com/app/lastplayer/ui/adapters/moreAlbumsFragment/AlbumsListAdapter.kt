package com.app.lastplayer.ui.adapters.moreAlbumsFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.data.MainListData
import com.app.lastplayer.data.remote.Album
import com.app.lastplayer.databinding.AlbumListItemBinding
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener
import com.app.lastplayer.ui.adapters.mainFragment.viewHolders.AlbumViewHolder

class AlbumsListAdapter : RecyclerView.Adapter<AlbumViewHolder>() {

    private val listAlbums = mutableListOf<Album>()
    var imageClickListener: ImageClickListener? = null

    fun addToList(list: List<Album>) {
        for (i in list.indices) {
            listAlbums.add(list[i])
            notifyItemInserted(listAlbums.size - 1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return AlbumViewHolder(
            AlbumListItemBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(
            MainListData.AlbumItem(listAlbums[position]),
            imageClickListener
        )
    }

    override fun getItemCount() = listAlbums.size
}