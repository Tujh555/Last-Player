package com.app.lastplayer.ui.adapters.mainFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.data.MainListData
import com.app.lastplayer.data.MainListItem
import com.app.lastplayer.databinding.ItemMainListBinding
import com.app.lastplayer.di.clickListenersComponent.ClickListenersComponent
import dagger.Lazy
import javax.inject.Inject

class MainListAdapter : RecyclerView.Adapter<MainListAdapter.MainViewHolder>() {
    private val mainListItems: MutableList<MainListItem> = mutableListOf()
    var seeMoreClickListener: SeeMoreClickListener? = null
    var clickListenerComponent: ClickListenersComponent? = null

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

    inner class MainViewHolder(
        private val mainHolderBinding: ItemMainListBinding
    ) : RecyclerView.ViewHolder(mainHolderBinding.root) {
        @Inject
        lateinit var dataAdapter: Lazy<MainListDataAdapter>

        init {
            clickListenerComponent?.inject(this)
        }

        fun bind(mainListItem: MainListItem) {
            mainHolderBinding.run {
                title.text = mainListItem.title

                if (mainListItem.title.lowercase() == "feeds") {
                    seeMore.visibility = View.GONE
                }

                seeMore.setOnClickListener {
                    seeMoreClickListener?.click(mainListItem.dataTypeCode, mainListItem.mainItems)
                }

                if (this@MainViewHolder::dataAdapter.isInitialized) {
                    dataAdapter.get().setList(mainListItem.mainItems)

                    itemsList.adapter = dataAdapter.get()
                }

                itemsList.layoutManager = LinearLayoutManager(
                    mainHolderBinding.root.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
        }
    }

    fun interface SeeMoreClickListener {
        fun click(dataTypeCode: Int, albums: List<MainListData>)
    }
}