package com.ricki.umbevents.View.Activity

import android.app.ProgressDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.ricki.umbevents.Helper.RegistrationEventHelper
import com.ricki.umbevents.Helper.SettingsHelper
import com.ricki.umbevents.R
import kotlinx.android.synthetic.main.activity_status_registration.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception

class ActivityStatusRegistration : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_registration)

        supportActionBar?.title = "Detail Registrasi"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var idEvent = intent.getStringExtra("idEvent")
        setContent(idEvent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var id = item?.itemId
        if(id == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun createQRCode(idRegister : String){
        var multiFormatWriter = MultiFormatWriter()
        try{
            var bitMatrix = multiFormatWriter.encode(idRegister, BarcodeFormat.QR_CODE, 350, 350)
            var barcodeEncoder = BarcodeEncoder()
            var bitmap = barcodeEncoder.createBitmap(bitMatrix)
            imageViewBarcode.setImageBitmap(bitmap)
        }catch (ex : Exception){
            ex.printStackTrace()
        }
    }

    fun setContent(idEvent : String){
        var progress = ProgressDialog(this)
        progress.setMessage("Sedang memuat...")
        progress.setCancelable(false)
        progress.show()

        doAsync {
            try{
                var idPart = SettingsHelper(this@ActivityStatusRegistration).id
                var data = RegistrationEventHelper().getDetailRegistration(idPart, idEvent)
                if(data != null){
                    uiThread {
                        textViewNameEvent.text = data.nameEvent
                        textViewIDPeserta.text = data.idParticipant
                        textViewName.text = data.name
                        textViewHarga.text = "Rp." + data.price

                        if(data.status == 1){
                            textViewStatus.setTextColor(Color.parseColor("#05a63b"))
                            textViewStatus.text = "Lunas"
                            textViewDescription.text = "Ini merupakan bukti pembayaran event"
                        }

                        createQRCode(data.id)

                        progress.dismiss()
                    }
                }else{
                    uiThread {
                        progress.dismiss()

                        Toast.makeText(this@ActivityStatusRegistration, "Registrasi tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                }
            }catch (ex : Exception){
                uiThread {
                    progress.dismiss()
                }
            }
        }
    }
}
