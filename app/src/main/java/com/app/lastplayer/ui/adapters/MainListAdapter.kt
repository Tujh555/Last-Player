package com.app.lastplayer.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.data.MainListItem
import com.app.lastplayer.databinding.ItemMainListBinding

class MainListAdapter : RecyclerView.Adapter<MainListAdapter.MainViewHolder>() {
    private val mainListItems: MutableList<MainListItem> = mutableListOf()

    fun setList(list: List<MainListItem>) {
        mainListItems.clear()
        mainListItems.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return MainViewHolder(ItemMainListBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(mainListItems[position])
    }

    override fun getItemCount() = mainListItems.size

    class MainViewHolder(
        private val mainHolderBinding: ItemMainListBinding
    ) : RecyclerView.ViewHolder(mainHolderBinding.root) {
        private val dataAdapter by lazy { MainListDataAdapter() }

        fun bind(mainListItem: MainListItem) {
            mainHolderBinding.run {
                title.text = mainListItem.title

                dataAdapter.setList(mainListItem.mainItems)
                itemsList.adapter = dataAdapter
                itemsList.layoutManager = LinearLayoutManager(
                    mainHolderBinding.root.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
        }
    }
}