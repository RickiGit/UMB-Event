package com.ricki.umbevents.View.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.ricki.umbevents.R
import kotlinx.android.synthetic.main.activity_qrcode.*
import java.lang.Exception

class QRCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)
    }

    fun createQRCode(idRegister : String){
        var multiFormatWriter = MultiFormatWriter()
        try{
            var bitMatrix = multiFormatWriter.encode(idRegister, BarcodeFormat.QR_CODE, 350, 350)
            var barcodeEncoder = BarcodeEncoder()
            var bitmap = barcodeEncoder.createBitmap(bitMatrix)
            imageViewQRRegistrasi.setImageBitmap(bitmap)
        }catch (ex : Exception){
            ex.printStackTrace()
        }
    }
}
