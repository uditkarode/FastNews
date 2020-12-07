package com.ukexample.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.ukexample.R
import com.ukexample.models.News

class NewsAdapter(private val news: ArrayList<News>): RecyclerView.Adapter<NewsAdapter.NewsHolder>() {
    /* number of elements in RecyclerView */
    override fun getItemCount() = news.size

    /* this will almost never change, just replace the
    *  name of your ViewHolder and XML as/when needed */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        = NewsHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false))

    /* this is what executes getItemCount() times for each element */
    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val current = news[position]

        holder.title.text = current.title

        if(current.author != "null")
            holder.author.text = current.author
        else
            holder.author.text = "Unknown"

        Glide.with(holder.itemView.context)
            .load(current.imgUrl)
            .into(holder.img)
    }

    /* here you define views that your element view has */
    class NewsHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<RoundedImageView>(R.id.news_image)
        val title = itemView.findViewById<TextView>(R.id.news_title)
        val author = itemView.findViewById<TextView>(R.id.news_author)
    }
}
