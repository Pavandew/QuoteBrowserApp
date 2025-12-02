package com.example.quotesbrowserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.quotesbrowserapp.model.PicsumItem
import com.example.quotesbrowserapp.databinding.ItemQuoteBinding

class QuoteAdapter(
    private val list: List<PicsumItem>,
    private val onClick: (PicsumItem) -> Unit
) : RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    inner class QuoteViewHolder(val binding: ItemQuoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val binding = ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item = list[position]
        holder.binding.quoteAuthor.text = item.author
        holder.binding.quoteImage.load(item.downloadUrl)
        holder.binding.root.setOnClickListener { onClick(item) }
    }

    override fun getItemCount(): Int = list.size
}