package com.example.myproducts.ui.productlist

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myproducts.R
import com.example.myproducts.databinding.ItemProductsBinding
import com.example.myproducts.responses.productlist.Product
import com.example.myproducts.ui.productdetails.ProductDetailActivity

class ProductAdapter(private val onFavoriteClick: ((productId: String) -> Unit?)) :
    RecyclerView.Adapter<MainViewHolder>() {

    private var products = mutableListOf<Product>()

    @SuppressLint("NotifyDataSetChanged")
    fun setProductList(products: List<Product>) {
        this.products = products.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductsBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val product = products[position]
        holder.binding.name.text = product.title
        if (product.price.isNotEmpty()) {
            holder.binding.price.text = product.price[0].value.toString()
        } else {
            holder.binding.price.text = ""
        }
        Glide.with(holder.itemView.context).load(product.imageURL)
            .placeholder(R.drawable.placeholder).into(holder.binding.imageview)
        holder.binding.ivFavorite.setOnClickListener {
            onFavoriteClick.invoke(product.id)
//            notifyItemChanged(position)
        }
        if (product.isFavorite) {
            holder.binding.ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.binding.ivFavorite.context,
                    R.drawable.filled_heart
                )
            )
        } else {
            holder.binding.ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.binding.ivFavorite.context,
                    R.drawable.heart
                )
            )
        }
        holder.binding.root.setOnClickListener {
            val intent =
                Intent(
                    holder.binding.root.context,
                    ProductDetailActivity::class.java
                )
            intent.putExtra(ProductDetailActivity.productInfo, product)
            holder.binding.root.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}

class MainViewHolder(val binding: ItemProductsBinding) : RecyclerView.ViewHolder(binding.root)