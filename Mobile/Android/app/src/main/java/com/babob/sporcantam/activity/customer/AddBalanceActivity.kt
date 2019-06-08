package com.babob.sporcantam.activity.customer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.babob.sporcantam.R
import kotlinx.android.synthetic.main.activity_add_balance.*


class AddBalanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_balance)

        var cardForm = card_form
        var addBalance = button_addBalance


        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(this)
    }


}
