package com.example.sskplatform.ui.appeals

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sskplatform.R

class ViolationAdapter(val context: Context, private val violations: List<ViolationModel>):
    RecyclerView.Adapter<ViolationViewHolder>() {

    override fun getItemCount(): Int {
        return violations.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViolationViewHolder {
        return ViolationViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViolationViewHolder, position: Int) {
        val violation = violations[position]
        holder.title.text = violation.title
        holder.message.text = violation.message
        holder.date.text = violation.date

        Glide.with(context)
            .load(violation.image)
            .into(holder.image)
    }

}

class ViolationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val title: TextView = itemView.findViewById(R.id.item_title)
    val message: TextView = itemView.findViewById(R.id.item_description)
    val image: ImageView = itemView.findViewById(R.id.imageView)
    val date: TextView = itemView.findViewById(R.id.item_date)
}

