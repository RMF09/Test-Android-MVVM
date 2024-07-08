package com.rmf.testandroidmvvm.presentation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rmf.testandroidmvvm.R
import com.rmf.testandroidmvvm.data.remote.response.Joke

class JokeAdapter(private var jokes: List<Joke>) : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_joke, parent, false)
        return JokeViewHolder(view)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.jokeTextView.text = jokes[position].value
    }

    override fun getItemCount(): Int = jokes.size

    fun setJokes(jokes: List<Joke>) {
        this.jokes = jokes
        notifyDataSetChanged()
    }

    class JokeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val jokeTextView: TextView = itemView.findViewById(R.id.jokeTextView)
    }
}