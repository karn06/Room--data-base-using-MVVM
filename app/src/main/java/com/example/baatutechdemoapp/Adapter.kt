package com.example.baatutechdemoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class Adapter : RecyclerView.Adapter<Adapter.RecyclerViewHolder>() {

    var pages = ArrayList<User>()
    lateinit var context: Context


    fun setData(context: Context, pages: ArrayList<User>) {
        this.pages = pages
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {


        val pagePosition = pages[position]

        holder.apply {

            title.text = pagePosition.name
        }
    }

    override fun getItemCount(): Int {
        return pages.size
    }

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.description
    }

}