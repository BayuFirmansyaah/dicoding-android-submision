package com.example.androidsubmission

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListLokomotifAdapter(private val listLokomotif: ArrayList<Lokomotif>) : RecyclerView.Adapter<ListLokomotifAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback?) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemCount(): Int = listLokomotif.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ListViewHolder{
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_lokomotif, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (title, description, photo) = listLokomotif[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .into(holder.img_lokomotif)
        holder.title_lokomotif.text = title
        holder.desc_lokomotif.text  = description

        holder.itemView.setOnClickListener{ v ->
            onItemClickCallback?.onItemClicked(listLokomotif.get(holder.adapterPosition))

        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img_lokomotif : ImageView = itemView.findViewById(R.id.img_lokomotif)
        val title_lokomotif: TextView = itemView.findViewById(R.id.title_lokomotif)
        val desc_lokomotif : TextView = itemView.findViewById(R.id.desc_lokomotif)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Lokomotif?)
    }
}