package com.ricki.umbevents.View.Activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.zxing.Result
import com.ricki.umbevents.Helper.AttendanceHelper
import com.ricki.umbevents.Helper.SettingsHelper
import com.ricki.umbevents.R
import kotlinx.android.synthetic.main.activity_scan_qr.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ActivityScanQR : AppCompatActivity(), ZXingScannerView.ResultHandler, View.OnClickListener {

    private lateinit var mScannerView: ZXingScannerView
    private var isCaptured = false
    var dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")

    var idevent = ""
    var status : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qr)

        supportActionBar?.title = "Absen"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var intent = intent
        status = intent.getIntExtra("status", 1)
        idevent = intent.getStringExtra("idevent")

        initScannerView()
        initDefaultView()
        button_reset.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var id = item?.itemId
        if(id == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initScannerView() {
        mScannerView = ZXingScannerView(this)
        mScannerView.setAutoFocus(true)
        mScannerView.setResultHandler(this)
        frame_layout_camera.addView(mScannerView)
    }

    override fun onStart() {
        mScannerView.startCamera()
        doRequestPermission()
        super.onStart()
    }

    private fun doRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            100 -> {
                initScannerView()
            }
            else -> {
                /* nothing to do in here */
            }
        }
    }

    override fun onPause() {
        mScannerView.stopCamera()
        super.onPause()
    }

    private fun initDefaultView() {
        text_view_qr_code_value.text = "Scanning QRCode..."
        button_reset.visibility = View.GONE
    }

    override fun handleResult(rawResult: Result?) {
        CheckInEvent(rawResult?.text!!)
        button_reset.visibility = View.VISIBLE
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.button_reset -> {
                val returnIntent = Intent()
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
            else -> {
                /* nothing to do in here */
            }
        }
    }

    fun CheckInEvent(qrcode : String){
        var result = qrcode
        var qrCodeAbsent = result.substring(result.lastIndexOf("_") + 1)
        var eventAbsent = dateFormat.parse(qrCodeAbsent)

        var now = Date()
        var currentDate = dateFormat.format(now)
        var absent = dateFormat.parse(currentDate)

        val diff = absent.getTime() - eventAbsent.getTime()
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        if(seconds < 60){
            if(status == 1){
                doAsync {
                    try{
                        var result = AttendanceHelper().checkInAttendance(SettingsHelper(this@ActivityScanQR).id, idevent)
                        if(result){
                            uiThread {
                                Toast.makeText(this@ActivityScanQR, "Success to Checkin", Toast.LENGTH_LONG).show()
                                var intent = Intent()
                                setResult(Activity.RESULT_OK, intent)
                                finish()
                            }
                        }else{
                            uiThread {
                                text_view_qr_code_value.text = "Failed to Checkin"
                                Toast.makeText(this@ActivityScanQR, "Failed to Checkin", Toast.LENGTH_LONG).show()
                            }
                        }
                    }catch (ex : Exception){

                    }
                }
            }else{
                doAsync {
                    try{
                        var result = AttendanceHelper().checkOutAttendance(SettingsHelper(this@ActivityScanQR).id, idevent)
                        if(result){
                            uiThread {
                                Toast.makeText(this@ActivityScanQR, "Success to Checkout", Toast.LENGTH_LONG).show()
                                var intent = Intent()
                                setResult(Activity.RESULT_OK, intent)
                                finish()
                            }
                        }else{
                            uiThread {
                                text_view_qr_code_value.text = "Failed to Checkout"
                                Toast.makeText(this@ActivityScanQR, "Failed to Checkout", Toast.LENGTH_LONG).show()
                            }
                        }
                    }catch (ex : Exception){

                    }
                }
            }
        }else{
            Toast.makeText(this, "QR Code Expired", Toast.LENGTH_LONG).show()
        }
    }
}
