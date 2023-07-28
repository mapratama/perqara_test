package com.angga.perqara.ui.viewholders

import coil.load
import com.angga.perqara.base.BaseViewHolder
import com.angga.perqara.databinding.ItemProductBinding
import com.angga.perqara.domain.model.Product

class ProductHolder(private val binding: ItemProductBinding): BaseViewHolder<Product>(binding) {

    override fun bind(item: Product) {
        binding.ivImage.load(item.imageUrl)
        binding.tvTitle.text = item.title
        binding.tvReleaseDate.text = "Release date 2021-08-30"
        binding.tvRating.text = "4.5"
    }

}
