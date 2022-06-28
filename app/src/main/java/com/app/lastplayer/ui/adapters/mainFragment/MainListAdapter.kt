package com.app.lastplayer.ui.adapters.mainFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.lastplayer.data.MainListData
import com.app.lastplayer.data.MainListItem
import com.app.lastplayer.data.remote.Album
import com.app.lastplayer.databinding.ItemMainListBinding
import com.app.lastplayer.ui.MainDataType
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener
import kotlin.reflect.KClass

class MainListAdapter : RecyclerView.Adapter<MainListAdapter.MainViewHolder>() {
    private val mainListItems: MutableList<MainListItem> = mutableListOf()
    private val imageClickListeners = mutableMapOf<Int, ImageClickListener>()
    var seeMoreClickListener: SeeMoreClickListener? = null

    fun setList(list: List<MainListItem>) {
        mainListItems.clear()
        mainListItems.addAll(list)
        notifyDataSetChanged()
    }

    fun setImageClickListenerOn(dataTypeCode: Int, listener: ImageClickListener) {
        imageClickListeners[dataTypeCode] = listener
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
        private val dataAdapter by lazy { MainListDataAdapter() }

        fun bind(mainListItem: MainListItem) {
            mainHolderBinding.run {
                title.text = mainListItem.title
                seeMore.setOnClickListener {
                    seeMoreClickListener?.click(mainListItem.dataTypeCode, mainListItem.mainItems)
                }

                dataAdapter.setList(mainListItem.mainItems)
                dataAdapter.setClickListeners(imageClickListeners)

                itemsList.adapter = dataAdapter
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