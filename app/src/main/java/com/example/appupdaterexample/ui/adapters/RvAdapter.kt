package com.example.appupdaterexample.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appupdaterexample.R
import com.example.appupdaterexample.data.models.ModelSimpleItem
import com.example.appupdaterexample.data.models.ModelUpdateBannerItem

private const val keyDefault = -1
private const val itemSimpleKey = 99
private const val itemUpdateKey = 333

class RvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Any> = emptyList()
    var onUpdateClicked: (ModelUpdateBannerItem) -> Unit = {}

    override fun getItemViewType(position: Int): Int {
        return when (items.get(position)) {
            is ModelSimpleItem -> itemSimpleKey
            is ModelUpdateBannerItem -> itemUpdateKey
            else -> keyDefault
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            itemSimpleKey -> {
                val view = parent.getLayoutInflater().inflate(R.layout.item_simple, parent, false)
                VhSimple(view)
            }
            itemUpdateKey -> {
                val view =
                    parent.getLayoutInflater().inflate(R.layout.item_update_banner, parent, false)
                VhModelUpdateBanner(view)
            }
            else -> throw IllegalArgumentException("Unknown view item type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items.get(position)
        when (item) {
            is ModelSimpleItem -> {
                (holder as VhSimple).bindItem(item)
            }
            is ModelUpdateBannerItem -> {
                (holder as VhModelUpdateBanner).btnUpdate.setOnClickListener {
                    onUpdateClicked.invoke(item)
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<Any>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class VhModelUpdateBanner(view: View) : RecyclerView.ViewHolder(view) {
    val btnUpdate = view.findViewById<Button>(R.id.btnUpdate)
}

class VhSimple(view: View) : RecyclerView.ViewHolder(view) {

    private val tv = view as TextView

    fun bindItem(item: ModelSimpleItem) {
        tv.text = item.text
    }
}

private fun View.getLayoutInflater() = LayoutInflater.from(this.context)