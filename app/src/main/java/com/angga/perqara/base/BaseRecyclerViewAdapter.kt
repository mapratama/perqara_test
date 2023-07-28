package com.angga.perqara.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        getViewHolder(parent, viewType)

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): VH

}