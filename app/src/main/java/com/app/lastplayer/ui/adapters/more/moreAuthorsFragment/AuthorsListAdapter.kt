package com.app.lastplayer.ui.adapters.more.moreAuthorsFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.app.lastplayer.data.MainListData
import com.app.lastplayer.data.remote.Author
import com.app.lastplayer.databinding.AlbumListItemBinding
import com.app.lastplayer.databinding.AuthorListItemBinding
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener
import com.app.lastplayer.ui.adapters.viewHolders.AlbumViewHolder
import com.app.lastplayer.ui.adapters.viewHolders.AuthorViewHolder

private val AUTHOR_COMPARATOR = object : DiffUtil.ItemCallback<Author>() {
    override fun areContentsTheSame(oldItem: Author, newItem: Author): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Author, newItem: Author): Boolean {
        return oldItem.id == newItem.id
    }
}

class AuthorsListAdapter : PagingDataAdapter<Author, AuthorViewHolder>(AUTHOR_COMPARATOR) {
    var imageClickListener: ImageClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return AuthorViewHolder(
            AuthorListItemBinding.inflate(inflater, parent, false).apply {
                root.layoutParams = ViewGroup.LayoutParams(
                    parent.layoutParams.width,
                    root.layoutParams.height
                )
            }
        )
    }

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(
                MainListData.AuthorItem(item),
                imageClickListener
            )
        }
    }

}