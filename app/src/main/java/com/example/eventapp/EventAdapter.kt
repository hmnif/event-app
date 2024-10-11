package com.example.eventapp

import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventapp.Response.ListEventsItem

class EventAdapter(
    private val listEvent: List<ListEventsItem>,
    private val listener: OnEventClickListener,
    private val width: Int? = null
) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    interface OnEventClickListener {
        fun onEventClick(eventId: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.image_item_event)
        val itemText: TextView = view.findViewById(R.id.title_item_event)
        val itemCard: CardView = view.findViewById(R.id.card_item_event)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (width != null) {
            holder.itemCard.layoutParams.width = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                width.toFloat(),
                holder.itemView.context.resources.displayMetrics
            ).toInt()
        }
        val itemEvent = listEvent[position]
        Log.d("EventAdapter", "onBindViewHolder: $itemEvent")
        holder.itemText.text = itemEvent.name
        Glide.with(holder.itemView.context)
            .load(itemEvent.mediaCover)
            .into(holder.itemImage)
        holder.itemView.setOnClickListener {
            listener.onEventClick(itemEvent.id)
        }
    }

    override fun getItemCount(): Int = listEvent.size


}