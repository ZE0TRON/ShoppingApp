package com.babob.sporcantam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.utility.ActivityOpenerUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        title = getString(R.string.sign_up_activity_title)

        button_signUpButton.setOnClickListener { signUp() }
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

        if(! isEmailValid(email)){
            Toast.makeText(this, getString(R.string.sign_up_activity_toast_invalid_email), Toast.LENGTH_SHORT).show()
            return
        }
        if(password.length < 6){
            Toast.makeText(this, getString(R.string.sign_up_activity_toast_invalid_password), Toast.LENGTH_SHORT).show()
            return
        }

        //TODO: Implement api communication

        givenEmail = editText_signUpEmail.text
        isSignedUp = true
        ActivityOpenerUtil.openLoginActivity(this)
        finish()

    }

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    fun isEmailValid(email: String): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    companion object {
        var isSignedUp = false
        lateinit var givenEmail: Editable
    }

}
