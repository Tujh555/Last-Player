package com.app.lastplayer.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.R
import com.app.lastplayer.data.remote.Track
import com.app.lastplayer.databinding.ItemListTrackBinding
import com.app.lastplayer.toTrackDuration
import com.app.lastplayer.ui.adapters.clickListeners.AddToFavoritesClickListener
import com.app.lastplayer.ui.adapters.clickListeners.RemoveFromFavorites
import com.app.lastplayer.ui.adapters.clickListeners.TrackClickListener
import com.bumptech.glide.RequestManager
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class TrackAdapter @Inject constructor(
    private val auth: FirebaseAuth,
    private val glideRequestManager: RequestManager
) : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {
    private val trackList = mutableListOf<Track>()
    private val trackListCopy by lazy { trackList.toList() }
    val tracksCount: Int
        get() = trackList.size

    var trackClickListener: TrackClickListener? = null
    var addToFavoritesClickListener: AddToFavoritesClickListener? = null
    var removeFromFavoritesClickListener: RemoveFromFavorites? = null

    fun setList(list: List<Track>) {
        trackList.clear()
        trackList.addAll(list)
        notifyDataSetChanged()
    }

    fun filterList(etText: String) {
        val newList = mutableListOf<Track>()

        for (track in trackListCopy) {
            if (etText.lowercase() in track.authorName.lowercase()
                || etText.lowercase() in track.name.lowercase()
            ) {
                newList.add(track)
            }
        }

        setList(newList)
    }

    fun removeTrack(track: Track) {
        var ind = -1

        for (i in trackList.indices) {
            if (trackList[i].id == track.id) {
                trackList.removeAt(i)
                ind = i
                break
            }
        }

        if (ind != -1) {
            notifyItemRemoved(ind)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return TrackViewHolder(
            ItemListTrackBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])
    }

    override fun getItemCount() = trackList.size

    inner class TrackViewHolder(
        private val binding: ItemListTrackBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(track: Track) {
            binding.run {
                glideRequestManager.load(track.image)
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_music_note_24)
                    .into(trackImage)

                trackImage.setOnClickListener {
                    trackClickListener?.click(
                        trackList.map { it.sharedData },
                        bindingAdapterPosition
                    )
                }

                if (removeFromFavoritesClickListener == null) {
                    addToFavorites.visibility = View.VISIBLE
                    removeFromFavorites.visibility = View.GONE
                } else {
                    addToFavorites.visibility = View.GONE
                    removeFromFavorites.visibility = View.VISIBLE
                }

                removeFromFavorites.setOnClickListener {
                    auth.currentUser?.let {
                        removeFromFavoritesClickListener?.click(track.toTrackEntity(it.uid))
                    }
                }

                addToFavorites.setOnClickListener {
                    auth.currentUser?.let {
                        addToFavoritesClickListener?.click(track.toTrackEntity(it.uid))
                    }
                }

                trackName.text = track.name
                authorName.text = track.authorName
                trackDuration.text = track.duration.toTrackDuration()
            }
        }
    }
}