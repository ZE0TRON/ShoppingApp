package com.babob.sporcantam.activity.admin

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.babob.sporcantam.R
import com.babob.sporcantam.utility.ActivityOpenerUtil
import kotlinx.android.synthetic.main.activity_admin_nav_main_page.*
import kotlinx.android.synthetic.main.app_bar_admin_nav_main_page.*

class AdminNavMainPage : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_nav_main_page)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.admin_nav_main_page, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_manipulate_customers -> {
                ActivityOpenerUtil.openManipulateCustomersActivity(this)
            }
            R.id.nav_manipulate_sellers -> {
                ActivityOpenerUtil.openManipulateSellersActivity(this)
            }
            R.id.nav_manipulate_items -> {
                ActivityOpenerUtil.openManipulateItemsActivity(this)
            }
            R.id.nav_manipulate_orders -> {
                ActivityOpenerUtil.openManipulateOrdersActivity(this)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
