package com.babob.sporcantam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.utility.ActivityOpenerUtil
import com.babob.sporcantam.utility.CheckerUtil
import kotlinx.android.synthetic.main.activity_sign_up.*
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.support.constraint.ConstraintSet







class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        title = getString(R.string.sign_up_activity_title)
        initSpinner()
        button_signUpButton.setOnClickListener { signUp() }
        spinner_signUpCustomerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateFrontend()
            }

        }
    }

    fun initSpinner(){
        val spinnerArray = ArrayList<String>()
        spinnerArray.add("Customer")
        spinnerArray.add("Seller")

        val adapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, spinnerArray)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val sItems = spinner_signUpCustomerType
        sItems.adapter = adapter

        spinner_signUpCustomerType.setSelection(0)
    }

    fun updateFrontend(){
        if(spinner_signUpCustomerType.selectedItemPosition == 1){
            //seller
            editText_signUpCompanyName.visibility = View.VISIBLE
        }
        else{
            //customer
            editText_signUpCompanyName.visibility = View.GONE

        }
    }

    fun signUp(){
        if( editText_signUpName.text.isEmpty() || editText_signUpSurname.text.isEmpty()){
            Toast.makeText(this, getString(R.string.sign_up_activity_toast_invalid_name), Toast.LENGTH_SHORT).show()
            return
        }
        val name = editText_signUpName.text.toString()
        val surname = editText_signUpSurname.text.toString()
        var email = ""
        if(!editText_signUpEmail.text.isEmpty()){
            email = editText_signUpEmail.text.toString()

        }
        var password = ""
        if(!editText_signUpPassword.text.isEmpty()){
            password = editText_signUpPassword.text.toString()

        }

        if(! CheckerUtil.emailChecker(email)){
            Toast.makeText(this, getString(R.string.sign_up_activity_toast_invalid_email), Toast.LENGTH_SHORT).show()
            return
        }
        if(! CheckerUtil.checkPassword(password)){
            Toast.makeText(this, getString(R.string.sign_up_activity_toast_invalid_password), Toast.LENGTH_SHORT).show()
            return
        }

        //TODO: Implement api communication

        givenEmail = editText_signUpEmail.text
        isSignedUp = true
        ActivityOpenerUtil.openLoginActivity(this)
        finish()

    }




    companion object {
        var isSignedUp = false
        lateinit var givenEmail: Editable
    }

}
