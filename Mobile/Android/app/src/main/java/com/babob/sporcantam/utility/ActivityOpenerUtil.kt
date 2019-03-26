package com.babob.sporcantam.utility

import android.content.Context
import android.content.Intent
import com.babob.sporcantam.activity.LoginActivity
import com.babob.sporcantam.activity.MainActivity
import com.babob.sporcantam.activity.SignUpActivity

class ActivityOpenerUtil {

    companion object {
        fun openLoginActivity(context: Context){
            context.startActivity(Intent(context, LoginActivity::class.java))
        }

        fun openSignUpActivity(context: Context){
            context.startActivity(Intent(context, SignUpActivity::class.java))
        }

        fun openMainActivity(context: Context){
            context.startActivity(Intent(context, MainActivity::class.java))

        }


    }
}