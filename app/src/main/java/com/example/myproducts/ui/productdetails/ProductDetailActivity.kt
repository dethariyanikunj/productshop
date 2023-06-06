package com.example.myproducts.ui.productdetails

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.myproducts.R
import com.example.myproducts.databinding.ActivityDetailsBinding
import com.example.myproducts.helper.Helpers
import com.example.myproducts.responses.productlist.Product
import com.example.myproducts.ui.productlist.ProductListFragment

class ProductDetailActivity : AppCompatActivity() {

    companion object {
        const val productInfo = "product_info"
    }

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val product = Helpers.getSerializable(intent, productInfo, Product::class.java)
        initProduct(product)
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initProduct(product: Product) {
        binding.name.text =
            resources.getString(R.string.title_name) + " " + product.title
        if (product.price.isNotEmpty()) {
            binding.price.text =
                resources.getString(R.string.title_price) + " " + product.price[0].value.toString()
        } else {
            binding.price.text = resources.getString(R.string.title_price) + " - "
        }
        Glide.with(this).load(product.imageURL)
            .placeholder(R.drawable.placeholder).into(binding.imageview)
        binding.ivFavorite.setOnClickListener {
            checkForFavorite(!product.isFavorite)
            product.isFavorite = !product.isFavorite
            ProductListFragment.onFavoriteChangeListener.onFavoriteChange(product.id)
        }
        binding.ratingBar.rating = product.ratingCount.toFloat()
        checkForFavorite(product.isFavorite)
    }

    private fun checkForFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            binding.ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.filled_heart
                )
            )
        } else {
            binding.ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.heart
                )
            )
        }
    }
}