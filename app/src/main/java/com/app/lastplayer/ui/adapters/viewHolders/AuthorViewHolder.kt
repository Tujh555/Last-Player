package com.app.lastplayer.ui.adapters.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.R
import com.app.lastplayer.appComponent
import com.app.lastplayer.data.MainListData
import com.app.lastplayer.databinding.AuthorListItemBinding

class AuthorViewHolder(
    private val binding: AuthorListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val glideRequestManager by lazy { itemView.context.appComponent.glideRequestManager }

    fun bind(_author: MainListData.AuthorItem) {
        val author = _author.item

        binding.run {
            glideRequestManager.load(author.image)
                .placeholder(R.drawable.ic_launcher_background)  // TODO(Make placeholder)
                .centerCrop()
                .into(authorImage)

            authorImage.setOnClickListener {
                TODO("Make author image click listener")
            }

            authorName.text = author.name

            if (listOf("https://", "http://").any { it in author.website }) {
                authorWebsite.text = author.website

                authorWebsite.setOnClickListener {
                    TODO("Make author website click listener")
                }
            }
        }
    }
}