package com.app.lastplayer.ui.adapters.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.R
import com.app.lastplayer.appComponent
import com.app.lastplayer.data.MainListData
import com.app.lastplayer.databinding.PlaylistListItemBinding

class PlaylistViewHolder(
    private val binding: PlaylistListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(_playlist: MainListData.PlaylistItem) {
        val playlist = _playlist.item

        binding.run {
            playlistName.text = playlist.name
            playlistOwner.text = playlist.userName
            playlistCreationDate.text = playlist.creationDate.replace("-", ".")

            playlistImage.setImageResource(R.drawable.ic_launcher_background)  // TODO(Make placeholder)

            playlistImage.setOnClickListener {
                TODO("Make playlist image click listener")
            }
        }
    }
}