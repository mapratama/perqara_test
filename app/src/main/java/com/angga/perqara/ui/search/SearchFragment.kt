package com.angga.perqara.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.angga.perqara.R
import com.angga.perqara.base.BaseFragment
import com.angga.perqara.databinding.SearchFragmentBinding
import com.angga.perqara.domain.model.Product
import com.angga.perqara.ui.callback.OnItemClickListener
import com.angga.perqara.utils.ext.hideSoftKeyboard
import com.angga.perqara.utils.ext.setOnTextChanged
import com.angga.perqara.utils.ext.showMessage
import kotlinx.coroutines.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<SearchFragmentBinding>() {

    private val viewModel: SearchViewModel by viewModel()

    private lateinit var productAdapter: ProductAdapter

    private var coroutineJob: Job? = null

    private var currentKeyword: String = ""

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> SearchFragmentBinding
        get() = SearchFragmentBinding::inflate

    override fun onCreated(view: View) {
        setupRecyclerView()
        setupSearchView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.products.observe(viewLifecycleOwner, {
            if (it.isEmpty())
                requireActivity().showMessage(binding.containerSearch, getString(R.string.label_no_data))
            else productAdapter.setData(it)
        })
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(onItemClickListener)
        binding.rvSearch.adapter = productAdapter
        binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())
    }

    private val onItemClickListener = object : OnItemClickListener<Product> {
        override fun onClick(item: Product) {
            val action = SearchFragmentDirections.actionSearchToDetails(item)
            findNavController().navigate(action)
        }

    }

    private fun setupSearchView() {
        binding.etSearch.setOnTextChanged { s, _, _, _ ->
            if (currentKeyword != s.toString()) {
                coroutineJob?.cancel()
                coroutineJob = GlobalScope.launch(Dispatchers.Main) {
                    delay(800)
                    currentKeyword = s.toString()
                    productAdapter.clear()
                    viewModel.searchProduct(s.toString())
                    requireActivity().hideSoftKeyboard()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineJob?.cancel()
    }

}