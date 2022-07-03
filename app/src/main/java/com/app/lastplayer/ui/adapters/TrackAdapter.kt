package com.app.lastplayer.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.R
import com.app.lastplayer.data.remote.Track
import com.app.lastplayer.databinding.ItemListTrackBinding
import com.app.lastplayer.toTrackDuration
import com.app.lastplayer.ui.adapters.clickListeners.TrackClickListener
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class TrackAdapter @Inject constructor(
    private val glideRequestManager: RequestManager
) : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {
    private val trackList = mutableListOf<Track>()
    var trackClickListener: TrackClickListener? = null

    fun setList(list: List<Track>) {
        trackList.clear()
        trackList.addAll(list)
        notifyDataSetChanged()
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
                    .placeholder(R.drawable.ic_launcher_background)  //TODO make placeholder
                    .into(trackImage)

                trackImage.setOnClickListener {
                    trackClickListener?.click(
                        trackList.map { it.sharedData },
                        bindingAdapterPosition
                    )
                }

                trackName.text = track.name
                authorName.text = track.authorName
                trackDuration.text = track.duration.toTrackDuration()
            }
        }
    }
}