package com.ricki.umbevents.View.Activity

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ricki.umbevents.Helper.AuthHelper
import com.ricki.umbevents.Helper.EventHelper
import com.ricki.umbevents.Helper.ParticipantTypeHelper
import com.ricki.umbevents.Model.ParticipantTypeModel
import com.ricki.umbevents.Model.RegisterModel
import com.ricki.umbevents.R
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {

    var listOfType : ArrayList<ParticipantTypeModel>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.title = "Registrasi"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getAllPartcipantType()
        register()
    }

    fun getAllPartcipantType(){
        doAsync {
            try{
                listOfType = ParticipantTypeHelper().getAllParticipantType()
                uiThread {
                    if(listOfType != null && listOfType!!.count() > 0){
                        var listType : ArrayList<String> = ArrayList()
                        for(i in 0 until listOfType!!.count()){
                            listType.add(listOfType!![i].name)
                        }

                        var adapter = ArrayAdapter<String>(this@RegisterActivity, android.R.layout.simple_spinner_dropdown_item, listType)
                        spinnerType.adapter = adapter
                    }
                }
            }catch (ex : Exception){
                uiThread {
                    Toast.makeText(this@RegisterActivity, "Gagal mendapatkan tipe anggota", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var id = item?.itemId
        if(id == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun register(){
        buttonRegister.setOnClickListener(View.OnClickListener {
            var nim = editTextNIM.text.toString()
            var name = editTextName.text.toString()
            var email = editTextEmail.text.toString()
            var phone = editTextPhone.text.toString()
            var gender = spinnerGender.selectedItem.toString()
            var type = listOfType!![spinnerGender.selectedItemPosition].id.toString()
            var username = editTextUsername.text.toString()
            var password = editTextPassword.text.toString()
            var confirm = editTextConfirmPassword.text.toString()

            if(nim.equals("") || name.equals("") || email.equals("") || phone.equals("") || gender.equals("") || type.equals("") || username.equals("") || password.equals("") || confirm.equals("")){
                Toast.makeText(this, "Harap isi semua form", Toast.LENGTH_SHORT).show()
            }else if(!password.equals(confirm)){
                Toast.makeText(this, "Password tidak sama dengan konfirmasi password", Toast.LENGTH_SHORT).show()
            }else{
                var dialog = ProgressDialog(this)
                dialog.setMessage("Sedang memproses...")
                dialog.setCancelable(false)
                dialog.show()

                doAsync {
                    var result = AuthHelper().register(RegisterModel(nim, name, email, phone, gender, "", type, username, password))
                    uiThread {
                        if(result.equals("success")){
                            Toast.makeText(this@RegisterActivity, "Registrasi berhasil", Toast.LENGTH_LONG).show()
                            finish()
                        }else if(result.equals("exist")){
                            Toast.makeText(this@RegisterActivity, "Username tidak tersedia", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(this@RegisterActivity, "Gagal melakukan registrasi, coba lagi", Toast.LENGTH_LONG).show()
                        }

                        dialog.dismiss()
                    }
                }
            }

        })
    }
}
