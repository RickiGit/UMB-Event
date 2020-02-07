package com.ricki.umbevents.View.Fragment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ricki.umbevents.Adapter.EventAdapter
import com.ricki.umbevents.Adapter.OnItemClickListener
import com.ricki.umbevents.Helper.EventHelper
import com.ricki.umbevents.Model.EventModel
import com.ricki.umbevents.R
import com.ricki.umbevents.View.Activity.ActivityDetailEvent
import kotlinx.android.synthetic.main.fragment_event.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EventFragment : Fragment(), OnItemClickListener {

    var recyclerViewEvent : RecyclerView? = null
    var eventAdapter : EventAdapter? = null
    var listOfEvent : ArrayList<EventModel>? = ArrayList()
    var swipeRefreshLayout : SwipeRefreshLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_event, container, false)

        initializeLayout(root)
        setListEvent(root)
        refreshPage(root)

        return root
    }

    fun initializeLayout(view : View){
        recyclerViewEvent = view.findViewById(R.id.recyclerViewEvent)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
    }

    @SuppressLint("WrongConstant")
    fun setListEvent(view : View){
        var progressDialog = ProgressDialog(view.context)
        progressDialog.setMessage("Sedang memuat...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        doAsync {
            listOfEvent = EventHelper().getAllEvent()
            uiThread {
                if(listOfEvent != null && listOfEvent!!.count() > 0){
                    eventAdapter = EventAdapter(listOfEvent!!, this@EventFragment)
                    recyclerViewEvent?.layoutManager = LinearLayoutManager(view.context, OrientationHelper.VERTICAL,false)
                    recyclerViewEvent?.adapter = eventAdapter
                }else{
                    textViewNotFound.visibility = View.VISIBLE
                }

                progressDialog.dismiss()
            }
        }
    }

    override fun onItemClicked(events: EventModel) {
        var intent = Intent(activity, ActivityDetailEvent::class.java)
        intent.putExtra("from", "event")
        intent.putExtra("event", events)
        startActivity(intent)
    }

    fun refreshPage(view : View){
        swipeRefreshLayout?.setOnRefreshListener({
            swipeRefreshLayout?.isRefreshing = false
            setListEvent(view)
        })
    }
}