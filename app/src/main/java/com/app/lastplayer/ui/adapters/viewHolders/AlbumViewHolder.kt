package com.app.lastplayer.ui.adapters.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.R
import com.app.lastplayer.appComponent
import com.app.lastplayer.data.MainListData
import com.app.lastplayer.databinding.AlbumListItemBinding
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener

class AlbumViewHolder(
    private val binding: AlbumListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val glideRequestManager by lazy { itemView.context.appComponent.glideRequestManager }

    fun bind(
        _album: MainListData.AlbumItem,
        imageClickListener: ImageClickListener?
    ) {
        val album = _album.item

        binding.run {
            albumName.text = album.name
            authorName.text = album.authorName

            glideRequestManager.load(album.image)
                .placeholder(R.drawable.ic_launcher_background)  // TODO(Make placeholder)
                .centerCrop()
                .into(albumImage)

            dateOfRelease.text = album.releaseDate.replace("-", ".")

            albumImage.setOnClickListener {
                imageClickListener?.onClick(album.id, album.image)
            }
        }
    }
}