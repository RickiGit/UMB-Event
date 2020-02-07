package com.ricki.umbevents.View.Fragment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ricki.umbevents.Adapter.EventAdapter
import com.ricki.umbevents.Adapter.OnItemClickListener
import com.ricki.umbevents.Helper.EventHelper
import com.ricki.umbevents.Helper.SettingsHelper
import com.ricki.umbevents.Model.EventModel

import com.ricki.umbevents.R
import com.ricki.umbevents.View.Activity.ActivityDetailEvent
import kotlinx.android.synthetic.main.fragment_my_event.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MyEventFragment : Fragment(), OnItemClickListener {

    var recyclerViewMyEvent : RecyclerView? = null
    var eventAdapter : EventAdapter? = null
    var listOfEvent : ArrayList<EventModel>? = ArrayList()
    var swipeRefreshLayout : SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_my_event, container, false)

        initializeLayout(root)
        setListEvent(root)
        refreshPage(root)

        return root
    }

    fun initializeLayout(view : View){
        recyclerViewMyEvent = view.findViewById(R.id.recyclerViewMyEvent)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
    }

    @SuppressLint("WrongConstant")
    fun setListEvent(view : View){
        var progressDialog = ProgressDialog(view.context)
        progressDialog.setMessage("Sedang memuat...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        doAsync {
            listOfEvent = EventHelper().getAllMyEvent(SettingsHelper(view.context).id)
            uiThread {
                if(listOfEvent != null && listOfEvent!!.count() > 0){
                    eventAdapter = EventAdapter(listOfEvent!!, this@MyEventFragment)
                    recyclerViewMyEvent?.layoutManager = LinearLayoutManager(view.context, OrientationHelper.VERTICAL,false)
                    recyclerViewMyEvent?.adapter = eventAdapter
                }else{
                    textViewNotFound.visibility = View.VISIBLE
                }

                progressDialog.dismiss()
            }
        }
    }

    override fun onItemClicked(events: EventModel) {
        var intent = Intent(activity, ActivityDetailEvent::class.java)
        intent.putExtra("from", "myevent")
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
