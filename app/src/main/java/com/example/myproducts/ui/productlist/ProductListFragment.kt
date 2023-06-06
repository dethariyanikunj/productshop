package com.example.myproducts.ui.productlist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myproducts.R
import com.example.myproducts.data.network.Resource
import com.example.myproducts.databinding.FragmentProductListBinding
import com.example.myproducts.ui.base.OnFavoriteChangeListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment(R.layout.fragment_product_list), OnFavoriteChangeListener {

    private val viewModel: ProductListViewModel by activityViewModels()

    private lateinit var binding: FragmentProductListBinding

    companion object {
        lateinit var onFavoriteChangeListener: OnFavoriteChangeListener
    }

    private val adapter = ProductAdapter(onFavoriteClick = fun(productId) {
        viewModel.updateFavorites(productId)
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductListBinding.bind(view)

        onFavoriteChangeListener = this
        binding.recyclerview.adapter = adapter
        viewModel.productList()
        viewModel.progressObserver.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        viewModel.productListResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    adapter.setProductList(it.value.products)
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Failure!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.favoriteNotifyPosition.observe(viewLifecycleOwner) {
            adapter.notifyItemChanged(it)
        }
    }

    override fun onFavoriteChange(productId: String) {
        viewModel.updateFavorites(productId)
    }

}