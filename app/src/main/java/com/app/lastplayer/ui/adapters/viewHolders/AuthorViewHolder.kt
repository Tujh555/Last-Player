package com.app.lastplayer.ui.adapters.viewHolders

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.R
import com.app.lastplayer.appComponent
import com.app.lastplayer.data.MainListData
import com.app.lastplayer.databinding.AuthorListItemBinding
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener
import java.lang.Exception

class AuthorViewHolder(
    private val binding: AuthorListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val glideRequestManager by lazy { itemView.context.appComponent.glideRequestManager }

    fun bind(
        _author: MainListData.AuthorItem,
        imageClickListener: ImageClickListener?,
    ) {
        val author = _author.item

        binding.run {
            glideRequestManager.load(author.image)
                .placeholder(R.drawable.ic_launcher_background)  // TODO(Make placeholder)
                .centerCrop()
                .into(authorImage)

            authorImage.setOnClickListener {
                imageClickListener?.onClick(author.id, author.image)
            }

            authorName.text = author.name

            if (listOf("https://", "http://").any { it in author.website }) {
                authorWebsite.text = author.website

                authorWebsite.setOnClickListener {
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(authorWebsite.text.toString())
                    ).also { intent ->
                        try {
                            binding.root.context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(
                                binding.root.context,
                                "No application can handle this request.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}