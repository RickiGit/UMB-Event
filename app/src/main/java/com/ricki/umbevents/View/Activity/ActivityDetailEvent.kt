package com.ricki.umbevents.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.ricki.umbevents.Helper.AttendanceHelper
import com.ricki.umbevents.Helper.EventHelper
import com.ricki.umbevents.Helper.RegistrationEventHelper
import com.ricki.umbevents.Helper.SettingsHelper
import com.ricki.umbevents.Model.EventModel
import com.ricki.umbevents.Model.RegistrationEventModel
import com.ricki.umbevents.R
import kotlinx.android.synthetic.main.activity_detail_event.*
import kotlinx.android.synthetic.main.activity_detail_event.buttonRegister
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.text.DecimalFormat


class ActivityDetailEvent : AppCompatActivity() {

    var dateFormat = SimpleDateFormat("dd MMM yyyy", Locale("id","ID"))
    var timeFormat = SimpleDateFormat("HH:mm")
    val formatter = DecimalFormat("#.###")

    var event : EventModel? = null
    var from : String = ""

    var quotaAvailable : Int = 0
    var statusRegister : Boolean = false
    var statusCheckin : Boolean = true
    var statusCertificate : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        supportActionBar?.title = "Event"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        event = intent.getSerializableExtra("event") as? EventModel
        from = intent.getStringExtra("from")

        checkStatus()

        var startDate = dateFormat.format(event?.startDate)
        var endDate = dateFormat.format(event?.endDate)
        var startTime = timeFormat.format(event?.startDate)
        var endTime = timeFormat.format(event?.endDate)

        textViewTitle.text = event?.title
        textViewDate.text = startDate + " s/d " + endDate
        textViewTime.text = startTime + "-" + endTime
        textViewPlace.text = event?.location
        textViewPrice.text = "Rp." + formatter.format(event?.normalPrice)
        textViewQuota.text = event?.quota.toString()
        webViewContent.loadData(event?.description, "text/html", "UTF-8")

        var register = RegistrationEventModel(SettingsHelper(this).id, event!!.id, event!!.normalPrice)
        registrationEvent(register)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        var id = item?.itemId
        if(id == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun registrationEvent(register : RegistrationEventModel){
        buttonRegister.setOnClickListener(View.OnClickListener {
            if(from.equals("event")){
                if(statusRegister){
                    var progress = ProgressDialog(this)
                    progress.setMessage("Sedang memproses...")
                    progress.setCancelable(false)
                    progress.show()

                    doAsync {
                        var result = RegistrationEventHelper().registerEvent(register)
                        uiThread {
                            if(result){
                                val builder = AlertDialog.Builder(this@ActivityDetailEvent)
                                builder.setTitle("Informasi")
                                builder.setMessage("Harap melakukan pembayaran segera")

                                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                                    buttonRegister.visibility = View.GONE
                                    dialog.dismiss()
                                }
                                builder.show()
                            }else{
                                Toast.makeText(this@ActivityDetailEvent, "Gagal melakukan registrasi", Toast.LENGTH_LONG).show()
                            }

                            progress.dismiss()
                        }
                    }
                }else{
                    var intent = Intent(this, ActivityStatusRegistration::class.java)
                    intent.putExtra("idEvent", event?.id)
                    startActivity(intent)
                }
            }else{
                if(!statusCertificate){
                    if(!statusCheckin){
                        var intent = Intent(this, ActivityScanQR::class.java)
                        intent.putExtra("status", 1)
                        intent.putExtra("idevent", event?.id)
                        startActivityForResult(intent, 10)
                    }else{
                        var intent = Intent(this, ActivityScanQR::class.java)
                        intent.putExtra("status", 2)
                        intent.putExtra("idevent", event?.id)
                        startActivityForResult(intent, 10)
                    }
                }
            }
        })
    }

    fun checkStatus(){
        if(from.equals("event")){
            var dialog = ProgressDialog(this)
            dialog.setMessage("Sedang memuat konten...")
            dialog.setCancelable(false)
            dialog.show()

            doAsync {
                try{
                    quotaAvailable = EventHelper().getQuotaAvailable(event!!.id)
                    statusRegister = RegistrationEventHelper().getStatusRegistration(SettingsHelper(this@ActivityDetailEvent).id, event!!.id)

                    uiThread {
                        textViewQuotaAvailable.text = quotaAvailable.toString()

                        if(!statusRegister){
                            buttonRegister.text = "Detail Registrasi"
                        }

                        dialog.dismiss()
                    }
                }catch (ex : Exception){
                    dialog.dismiss()
                }
            }
        }else if(from.equals("myevent")){
            doAsync {
                try{
                    val date = Date() // this object contains the current date value

                    if(event?.endDate!!.after(date)){
                        statusCertificate = AttendanceHelper().getStatusCertificate(event!!.id, SettingsHelper(this@ActivityDetailEvent).id)
                        if(statusCertificate){
                            uiThread {
                                buttonRegister.text = "Certificate"
                            }
                        }else{
                            statusCheckin = AttendanceHelper().getStatusCheckIn(event!!.id, SettingsHelper(this@ActivityDetailEvent).id)
                            if(!statusCheckin){
                                uiThread {
                                    buttonRegister.text = "Checkin"
                                }
                            }else{
                                uiThread {
                                    buttonRegister.text = "Checkout"
                                }
                            }
                        }
                    }else{
                        buttonRegister.visibility = View.GONE
                    }
                }catch (ex : Exception){

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 10){
            checkStatus()
        }
    }
}
