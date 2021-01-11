package com.example.myapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.model.Offer
import com.squareup.picasso.Picasso

class OfferAdapter(private val mContext : Context,
                   private val image : List<Offer>
) : RecyclerView.Adapter<OfferAdapter.ViewHolder>()
{
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val offerimage : ImageView = itemView.findViewById(R.id.offer_image)

        fun bindOffers(offer : Offer)
        {
           Picasso.get().load(offer.getOfferImage()).into(offerimage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.offers_list_layout,parent,false))
    }

    override fun getItemCount(): Int {
       return image.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //image[position]
        holder.bindOffers(image[position])
    }

}