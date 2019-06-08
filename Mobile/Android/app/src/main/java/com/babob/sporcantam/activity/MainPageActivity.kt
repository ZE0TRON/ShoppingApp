package com.babob.sporcantam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.babob.sporcantam.R
import com.babob.sporcantam.utility.ActivityOpenerUtil

class MainPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        val type: Int = intent.getSerializableExtra("type") as Int
        when (type) {
            2 -> ActivityOpenerUtil.openSellerItemListActivity(this)
            3 -> ActivityOpenerUtil.openAdminNavMainPage(this)
            else -> ActivityOpenerUtil.openCustomerMainActivitty(this)
        }
        finish()


    }
}
