package com.ricki.umbevents.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ricki.umbevents.Model.EventModel
import com.ricki.umbevents.R
import kotlinx.android.synthetic.main.item_list_event.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    val title = itemView.textViewTitle
    val date = itemView.textViewDate

    var dateFormat = SimpleDateFormat("dd MMM yyyy", Locale("id","ID"))

    fun bind(event: EventModel, clickListener: OnItemClickListener)
    {
        title.text = event.title
        date.text = dateFormat.format(event.startDate)

        itemView.setOnClickListener {
            clickListener.onItemClicked(event)
        }
    }
}

class EventAdapter(var listOfEvent:ArrayList<EventModel>, val itemClickListener: OnItemClickListener):RecyclerView.Adapter<MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_event,parent,false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfEvent.size
    }

    override fun onBindViewHolder(myHolder: MyHolder, position: Int) {
        val events = listOfEvent.get(position)
        myHolder.bind(events, itemClickListener)
    }
}


interface OnItemClickListener{
    fun onItemClicked(events: EventModel)
}
