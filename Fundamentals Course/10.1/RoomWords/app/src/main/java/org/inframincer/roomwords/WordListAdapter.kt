package org.inframincer.roomwords

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class WordListAdapter(private val context: Context) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    var words: List<Word>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return words?.let {
            it.size
        } ?: 0
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words?.let {
            words!![position].word
        } ?: " No word"
        holder.wordItemView.text = word
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.textView
    }
}
