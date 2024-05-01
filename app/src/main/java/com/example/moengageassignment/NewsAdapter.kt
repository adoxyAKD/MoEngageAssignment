package com.example.moengageassignment

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private var articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.id_titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.id_descriptionTextView)
        val authorTextView: TextView = itemView.findViewById(R.id.id_authorTxvw)
        val publishAtTextView: TextView = itemView.findViewById(R.id.id_publishedAt)
        val imageView: ImageView = itemView.findViewById(R.id.id_NewsImageViewURL)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = articles[position]
        holder.titleTextView.text = currentItem.title
        holder.descriptionTextView.text = currentItem.description
        holder.authorTextView.text = currentItem.author
        holder.publishAtTextView.text = currentItem.publishedAt
        Glide.with(holder.itemView)
            .load(currentItem.urlToImage)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentItem.url))
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = articles.size

    fun updateArticles(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    fun sortByOldToNew() {
        articles = articles.sortedBy { it.publishedAt }
        notifyDataSetChanged()
    }

    fun sortByNewToOld() {
        articles = articles.sortedByDescending { it.publishedAt }
        notifyDataSetChanged()
    }
}
