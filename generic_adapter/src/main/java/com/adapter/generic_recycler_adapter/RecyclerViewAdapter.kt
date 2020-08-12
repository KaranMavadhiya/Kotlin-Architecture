package com.adapter.generic_recycler_adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 *  RecyclerViewAdapter takes 2 arguments:
 *  @param layout -> The item layout to be inflated
 *  @param callbacks -> The AdapterCallback interface which is used to set data to the UI and handle view clicks
 */
class RecyclerViewAdapter(private val layout: Int, private val callbacks: AdapterCallback) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val items = mutableListOf<Any>()


    /**
     *  Function to set a list of items to the RecyclerView
     */
    fun setItems(items: List<Any>) {
        this.items.clear()
        this.items.addAll(items)
        notifyItemRangeChanged(0,items.size - 1)
    }

    /**
     *  Function to add single item to the RecyclerView
     */
    fun addItem(item: Any) {
        this.items.add(item)
        notifyItemInserted(items.size - 1)
    }

    /**
     *  Function to add a list of items to the RecyclerView
     */
    fun addItems(items: List<Any>) {
        this.items.addAll(items)
        notifyItemRangeInserted(this.items.size - items.size, this.items.size - 1)
    }

    /**
     *  Function to clear the RecyclerView
     */
    fun clearItems() {
        this.items.clear()
        notifyItemRangeRemoved(0, items.size - 1)
    }

    /**
     *  The ViewHolder class which binds the data to the RecyclerView item
     */
    class ViewHolder(private val view: View, private val callbacks: AdapterCallback) : RecyclerView.ViewHolder(view) {

        fun bind(item: Any, position: Int) {
            callbacks.bindViews(view, item, position)

            view.setOnClickListener { callbacks.onViewClicked(view, item, position) }
        }
    }

    /**
     *  Function to inflate the layout item
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(viewGroup.inflate(layout), callbacks)
    }

    /**
     *  Function to get the total number of items in the RecyclerView
     */
    override fun getItemCount(): Int = items.size

    /**
     *  Function to bind each item in the items list to the RecyclerView at a given position
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }
}