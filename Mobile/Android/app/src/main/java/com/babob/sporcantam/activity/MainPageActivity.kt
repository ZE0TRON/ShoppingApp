package com.babob.sporcantam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.babob.sporcantam.R
import com.babob.sporcantam.item.Item
import com.babob.sporcantam.utility.ActivityOpenerUtil
import kotlinx.android.synthetic.main.activity_main_page.*

class MainPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        val type: Int = intent.getSerializableExtra("type") as Int
        when (type) {
            2 -> ActivityOpenerUtil.openSellerItemListActivity(this)
            3 -> ActivityOpenerUtil.openAdminNavMainPage(this)
            else -> ActivityOpenerUtil.openCustomerMainPageActivitty(this)
        }
        finish()


    }
}
