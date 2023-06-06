package com.example.myproducts.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myproducts.R
import com.example.myproducts.databinding.FragmentFavoriteBinding
import com.example.myproducts.ui.productlist.ProductAdapter
import com.example.myproducts.ui.productlist.ProductListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var binding: FragmentFavoriteBinding

    private val viewModel: ProductListViewModel by activityViewModels()

    private val adapter = ProductAdapter(onFavoriteClick = fun(productId) {
        viewModel.updateFavorites(productId)
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)

        val favorites = viewModel.favoriteProductList.value
        favorites?.let {
            adapter.setProductList(it)
        }
        binding.recyclerview.adapter = adapter
        viewModel.favoriteProductList.observe(viewLifecycleOwner) {
            adapter.setProductList(it)
        }
        viewModel.favoriteNotifyPosition.observe(viewLifecycleOwner) {
            adapter.notifyItemChanged(it)
        }
    }

}