package com.example.pettalk_a.board

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pettalk_a.R
import com.example.pettalk_a.fragments.BannerFragment

class BannerLVAdapter (val context : Context,
                       val items : ArrayList<BannerModel>,
                       val keyList : ArrayList<String>,
) : RecyclerView.Adapter<BannerLVAdapter.Viewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerLVAdapter.Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.banner_rv_item, parent, false)

        return Viewholder(v)
    }

    override fun onBindViewHolder(holder: BannerLVAdapter.Viewholder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item : BannerModel, key : String) {

            itemView.setOnClickListener {
                val intent = Intent(context, BannerFragment::class.java)
                intent.putExtra("url", item.url)
                itemView.context.startActivity(intent)
            }

            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)



            Glide.with(context)
                .load(item.url)
                .into(imageViewArea)

        }

    }


}

