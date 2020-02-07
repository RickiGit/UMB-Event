package com.ricki.umbevents.View.Fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.ricki.umbevents.Helper.SettingsHelper

import com.ricki.umbevents.R
import com.ricki.umbevents.View.Activity.LoginActivity
import com.ricki.umbevents.View.Activity.MainActivity
import com.ricki.umbevents.settings
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_profile, container, false)

        var textViewName = root.findViewById<TextView>(R.id.textViewName)
        var textViewEmail = root.findViewById<TextView>(R.id.textViewEmail)
        var textViewGender = root.findViewById<TextView>(R.id.textViewGender)
        var textViewPhone = root.findViewById<TextView>(R.id.textViewPhone)
        var buttonLogout = root.findViewById<Button>(R.id.buttonLogout)

        var name = SettingsHelper(root.context).name.toString()
        var email = SettingsHelper(root.context).email.toString()
        var gender = SettingsHelper(root.context).gender.toString()
        var phone = SettingsHelper(root.context).phone.toString()
        // Set Content
        textViewName.text = name
        textViewEmail.text = email
        textViewGender.text = gender
        textViewPhone.text = phone

        buttonLogout.setOnClickListener(View.OnClickListener {
            SettingsHelper(root.context).editPrefs.clear().commit()

            var activity = getActivity() as MainActivity

            var intent = Intent(activity, LoginActivity::class.java)
            activity?.startActivity(intent)
            activity!!.finish()
        })

        return root
    }
}
