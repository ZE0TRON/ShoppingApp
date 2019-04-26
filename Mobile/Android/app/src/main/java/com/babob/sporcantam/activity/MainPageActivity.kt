package com.babob.sporcantam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.babob.sporcantam.R
import com.babob.sporcantam.utility.ActivityOpenerUtil
import kotlinx.android.synthetic.main.activity_main_page.*

class MainPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        button_mainPageAddItem.setOnClickListener { ActivityOpenerUtil.openItemCreateActivity(this) }
    }
}
