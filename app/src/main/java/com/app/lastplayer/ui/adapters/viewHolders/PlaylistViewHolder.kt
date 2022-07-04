package com.app.lastplayer.ui.adapters.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.R
import com.app.lastplayer.data.MainListData
import com.app.lastplayer.databinding.PlaylistListItemBinding
import com.app.lastplayer.ui.adapters.clickListeners.AddTofavoritesClickListener
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener

class PlaylistViewHolder(
    private val binding: PlaylistListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        _playlist: MainListData.PlaylistItem,
        imageClickListener: ImageClickListener?,
    ) {
        val playlist = _playlist.item

        binding.run {
            playlistName.text = playlist.name
            playlistOwner.text = playlist.userName
            playlistCreationDate.text = playlist.creationDate.replace("-", ".")

            playlistImage.setImageResource(R.drawable.ic_baseline_playlist_play_24)

            playlistImage.setOnClickListener {
                imageClickListener?.onClick(
                    playlist.id,
                    "",
                )
            }
        }
    }
}