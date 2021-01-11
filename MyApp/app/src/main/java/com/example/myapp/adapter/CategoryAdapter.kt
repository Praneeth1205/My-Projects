package com.example.myapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.ProductDetailsActivity
import com.example.myapp.R
import com.example.myapp.model.Category
import com.squareup.picasso.Picasso

class CategoryAdapter (private val mContext : Context,
                       private val category : List<Category>
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>()
{
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val imageSrc = itemView.findViewById<ImageView>(R.id.product_image)
        val title = itemView.findViewById<TextView>(R.id.title_product)

        fun bind(category: Category)
        {
            title.text = category.getName()
            Picasso.get().load(category.getImage()).into(imageSrc)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext)
                .inflate(R.layout.category_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return category.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = category[position]
        holder.title.text = item.getName()
        Picasso.get().load(item.getImage()).into(holder.imageSrc)

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
           val intent = Intent(mContext,ProductDetailsActivity::class.java)
            intent.putExtra("title", holder.title.text.toString())
            mContext.startActivity(intent)

        }
    }

}