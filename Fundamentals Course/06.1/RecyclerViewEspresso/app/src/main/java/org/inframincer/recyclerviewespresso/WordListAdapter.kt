package org.inframincer.recyclerviewespresso

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class WordListAdapter(
    private val context: Context,
    private val words: MutableList<String>
) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): WordListAdapter.WordViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item_word, p0, false)
        return WordViewHolder(itemView, this, words)
    }

    override fun getItemCount(): Int {
        return words.size
    }

    override fun onBindViewHolder(p0: WordListAdapter.WordViewHolder, p1: Int) {
        val word = words[p1]
        p0.bindWord(word)
    }

    class WordViewHolder(
        itemView: View,
        private val adapter: WordListAdapter,
        private val words: MutableList<String>
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        fun bindWord(word: String) {
            itemView.findViewById<TextView>(R.id.word_text_view).text = word
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            // Get the position of the item that was clicked.
            val position = layoutPosition
            // Use that to access the affected item in wordList.
            val element = words[position]
            // Change the word in the words
            words[position] = "Clicked! $element"
            // Notify the adapter, that the data has changed so it can update the RecyclerView to display the data.
            adapter.notifyDataSetChanged()
        }
    }
}
