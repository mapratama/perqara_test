package com.angga.perqara.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.angga.perqara.R
import com.angga.perqara.base.BaseFragment
import com.angga.perqara.databinding.ProductDetailFragmentBinding
import com.angga.perqara.domain.model.Product
import org.koin.android.viewmodel.ext.android.viewModel

class ProductDetailFragment : BaseFragment<ProductDetailFragmentBinding>(), View.OnClickListener {

    private val viewModel: ProductDetailViewModel by viewModel()

    private var isFavorite = false

    private lateinit var product: Product

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ProductDetailFragmentBinding
        get() = ProductDetailFragmentBinding::inflate

    override fun onCreated(view: View) {
        initViews()

        val product = ProductDetailFragmentArgs.fromBundle(requireArguments()).product
        viewModel.setProduct(product)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.product.observe(viewLifecycleOwner, {
            product = it
            populateDataProduct(it)
        })
    }

    private fun populateDataProduct(item: Product) {
        binding.ivGameImage.load(item.imageUrl)
        binding.tvGameCategory.text = "Rockstar Games"
        binding.tvGameTitle.text = item.title
        binding.tvGameReleaseDate.text = "Release Date 10-09-2020"
        binding.tvGameRating.text = "4.5"
        binding.tvGamePlayed.text = "230 played"
        binding.tvGameDescription.text = "Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet"


        if (item.loved == 1)
            binding.tvGameRating.text = "LOVED"
        else
            binding.tvGameRating.text = "NO LOVED"
//        val status = item.loved == 1
//        setStatusFavorite(status)


    }

    private fun initViews() {
        binding.ivBack.setOnClickListener(this)
        binding.ivLoved.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.iv_loved -> {
                if (this::product.isInitialized) {
                    isFavorite = !isFavorite
                    viewModel.setFavoriteProduct(product, isFavorite)
                    setStatusFavorite(isFavorite)
                }
            }
            R.id.iv_back -> if (this::product.isInitialized) {
                val action = ProductDetailFragmentDirections.actionDetailToHistory()
                findNavController().navigate(action)
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) binding.ivLoved.load(R.drawable.ic_favorite_selected)
        else binding.ivLoved.load(R.drawable.ic_favorite)
    }

}