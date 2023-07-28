package com.angga.perqara.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.angga.perqara.R
import com.angga.perqara.base.BaseFragment
import com.angga.perqara.data.Resource
import com.angga.perqara.databinding.MainFragmentBinding
import com.angga.perqara.domain.model.Product
import com.angga.perqara.ui.callback.OnItemClickListener
import com.angga.perqara.ui.search.ProductAdapter
import com.angga.perqara.utils.ext.showMessage
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<MainFragmentBinding>(), View.OnClickListener {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var productAdapter: ProductAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> MainFragmentBinding
        get() = MainFragmentBinding::inflate

    override fun onCreated(view: View) {
        initViews()
        setupRecyclerView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.test()
        viewModel.getProduct()

        viewModel.home.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    viewModel.getProduct()
                }
                is Resource.Empty -> {
                    hideProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    requireActivity().showMessage(binding.main, it.message ?: getString(R.string.label_unexpected_error))
                }
            }
        })

        viewModel.products.observe(viewLifecycleOwner, {
            productAdapter.clear()
            productAdapter.setData(it)
        })
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(onItemClickListener)
        binding.rvMain.adapter = productAdapter
        binding.rvMain.layoutManager = LinearLayoutManager(requireContext())
    }

    private val onItemClickListener = object : OnItemClickListener<Product> {
        override fun onClick(item: Product) {
            val action = MainFragmentDirections.actionMainToDetails(item)
            findNavController().navigate(action)
        }

    }

    private fun initViews() {
        binding.searchView.setOnClickListener(this)
    }

    private fun hideProgressBar() {
        binding.pbLoading.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.search_view -> {
                val action = MainFragmentDirections.actionMainToSearch()
                findNavController().navigate(action)
            }
        }
    }

}