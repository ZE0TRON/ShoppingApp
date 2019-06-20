package com.babob.sporcantam.activity.customer

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.babob.sporcantam.R
import kotlinx.android.synthetic.main.activity_add_balance.*
import android.text.InputType
import android.widget.Toast
import com.babob.sporcantam.utility.*
import com.braintreepayments.cardform.view.CardForm


class AddBalanceActivity : AppCompatActivity() {

    lateinit var cardForm: CardForm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_balance)

        cardForm = card_form
        val addBalance = button_addBalance


        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(false)
                .mobileNumberRequired(false)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(this)
        cardForm.cvvEditText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD


        addBalance.setOnClickListener {
            if (cardForm.isValid && ! editText_AddBalance_chargeAmount.text.isEmpty()) {
                addBalance()
            }else {
                Toast.makeText(this, "Please complete the form", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun addBalance(){
        val num:Double = editText_AddBalance_chargeAmount.text.toString().toDouble()
        val expire = cardForm.expirationMonth + "-" + cardForm.expirationYear
        AsyncUtil{
            val responseList = CheckerUtil.responseListChecker(JsonUtil.generalServerResponseToList(HttpUtil.sendPoststr(
                    UrlParamUtil.paymentToUrlParam(cardForm.cardNumber, cardForm.cvv, expire, num),
                    "${getString(R.string.base_url)}/customer/addBalance", SessionUtil.getSessionId(this)!!)))
            if(responseList[0] == "true"){
                runOnUiThread{ Toast.makeText(this, "Adding Balance Successful", Toast.LENGTH_SHORT).show()}
                finish()
            }
            else {
                runOnUiThread{ Toast.makeText(this, responseList[1], Toast.LENGTH_SHORT).show()}
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }


}
