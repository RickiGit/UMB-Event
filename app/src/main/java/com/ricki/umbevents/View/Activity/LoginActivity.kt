package com.ricki.umbevents.View.Activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ricki.umbevents.Helper.AuthHelper
import com.ricki.umbevents.Helper.SettingsHelper
import com.ricki.umbevents.Model.ParticipantModel
import com.ricki.umbevents.R
import com.ricki.umbevents.settings
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception

class LoginActivity : AppCompatActivity(), CoroutineScope {
    lateinit var masterJob: Job
    override val coroutineContext: kotlin.coroutines.CoroutineContext
        get() = Dispatchers.Main + masterJob

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        if(SettingsHelper(this).id != null && !SettingsHelper(this).id.equals("")){
            var intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            login()
        }
        pageRegister()
    }

    fun login(){
        buttonLogin.setOnClickListener(View.OnClickListener {

            var username = editTextUsername.text.toString()
            var password = editTextPassword.text.toString()

            if(username.equals("")){
                Toast.makeText(this@LoginActivity, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if(password.equals("")){
                Toast.makeText(this@LoginActivity, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else {

                try{
                    var model : ParticipantModel? = null
                    var progress = ProgressDialog(this@LoginActivity)
                    progress.setMessage("Sedang memproses...")
                    progress.setCancelable(false)
                    progress.show()

                    doAsync {
                        try{

                            model = AuthHelper().checkUserLogin(username, password)
                            uiThread {
                                if(model == null){
                                    Toast.makeText(this@LoginActivity, "Username atau Password yang anda masukan salah", Toast.LENGTH_SHORT).show()
                                }else{
                                    SettingsHelper(this@LoginActivity).name = model!!.name
                                    SettingsHelper(this@LoginActivity).email = model!!.email
                                    SettingsHelper(this@LoginActivity).gender = model!!.gender
                                    SettingsHelper(this@LoginActivity).id = model!!.id
                                    SettingsHelper(this@LoginActivity).idType = model!!.idType
                                    SettingsHelper(this@LoginActivity).phone = model!!.phone
                                    SettingsHelper(this@LoginActivity).username = model!!.username

                                    var intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                                progress.dismiss()
                            }


                        }catch (ex : Exception){

                        }
                    }

                }catch (ex : Exception){

                }
            }
        })
    }

    fun pageRegister(){
        textViewRegister.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        })
    }
}
