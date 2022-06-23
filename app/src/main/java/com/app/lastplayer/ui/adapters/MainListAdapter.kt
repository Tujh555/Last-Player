package com.app.lastplayer.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.data.MainListItem
import com.app.lastplayer.databinding.ItemMainListBinding

class MainListAdapter : RecyclerView.Adapter<MainListAdapter.MainViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class ItemAdapter(item: MainListItem) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }

        inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view)
    }
}