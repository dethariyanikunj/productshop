package com.example.myproducts.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myproducts.repository.BaseRepository
import com.example.myproducts.repository.ProductListRepository
import com.example.myproducts.ui.productlist.ProductListViewModel

class ViewModelFactory(private val repository: BaseRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductListViewModel::class.java) -> ProductListViewModel(
                repository as ProductListRepository
            ) as T
            else -> throw IllegalArgumentException("view model class not found")
        }
    }
}