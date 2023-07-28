package com.angga.perqara.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angga.perqara.base.BaseRecyclerViewAdapter
import com.angga.perqara.databinding.ItemProductBinding
import com.angga.perqara.databinding.ItemProgressBinding
import com.angga.perqara.domain.model.Product
import com.angga.perqara.ui.callback.OnItemClickListener
import com.angga.perqara.ui.viewholders.LoadingHolder
import com.angga.perqara.ui.viewholders.ProductHolder

class ProductAdapter(private val onItemClickListener: OnItemClickListener<Product>): BaseRecyclerViewAdapter<RecyclerView.ViewHolder>() {

    private var isLoading = false

    private val items = mutableListOf<Product>()

    fun setData(items: List<Product>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            PRODUCT_DATA -> {
                val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
                ProductHolder(binding)
            }
            else -> {
                val binding = ItemProgressBinding.inflate(layoutInflater, parent, false)
                LoadingHolder(binding)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            PRODUCT_DATA -> {
                val item = items[position]
                holder as ProductHolder
                holder.bind(item)
                holder.itemView.setOnClickListener {
                    onItemClickListener.onClick(item)
                }
            }
        }

    }

    override fun getItemCount(): Int = items.size + if (isLoading) 1 else 0

    override fun getItemViewType(position: Int): Int {
        return if (isLoading && position == itemCount - 1) {
            LOADING
        } else {
            PRODUCT_DATA
        }
    }

    fun hideLoading() {
        isLoading = false
        notifyItemRemoved(itemCount - 1)
    }

    fun showLoading() {
        isLoading = true
        notifyItemInserted(itemCount + 1)
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }

    companion object {
        const val PRODUCT_DATA = 0
        const val LOADING = 1
    }
}