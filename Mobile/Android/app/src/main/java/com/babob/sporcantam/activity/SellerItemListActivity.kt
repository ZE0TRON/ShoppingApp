package com.babob.sporcantam.activity

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.babob.sporcantam.R
import com.babob.sporcantam.adapter.RecyclerSellerItemAdapter
import com.babob.sporcantam.item.Item
import com.babob.sporcantam.utility.*
import kotlinx.android.synthetic.main.activity_seller_item_list.*

class SellerItemListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    lateinit var dataset:ArrayList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_item_list)

        title = "Seller Items"

        dataset = arrayListOf()

        viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerSellerItemAdapter(dataset, this)


        recyclerView = findViewById<RecyclerView>(R.id.recyclerview_sellerItemList).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter


        }

        button_openAddItem.setOnClickListener { ActivityOpenerUtil.openItemCreateActivity(this) }
        button_updateSellerInfo.setOnClickListener { ActivityOpenerUtil.openUpdateSellerInfoActivity(this) }
        button_sellerItemList_logout.setOnClickListener { logout() }
    }

    fun getBalance():String{
        val response = HttpUtil.sendPoststr(
                "", "${getString(R.string.base_url)}/seller/getBalance", SessionUtil.getSessionId(this)!!)
        return if(! response.isEmpty()){
            response
        }
        else {
            "0"
        }
    }

    override fun onResume() {
        super.onResume()
        AsyncUtil{
            updateList()
            val bal = getBalance()
            runOnUiThread { textView59.text = bal }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

    }

    fun logout(){
        SessionUtil.logOut(this)
        ActivityOpenerUtil.openCustomerMainActivitty(this)
        finish()
    }

    fun updateList(){
        dataset = JsonUtil.getItemResponseToList(
                HttpUtil.sendPoststr(
                        "","${getString(R.string.base_url)}/seller/my-items", SessionUtil.getSessionId(this)!!))

        if(dataset.size == 0){
            runOnUiThread { textView_sellerItemActivityNoItem.visibility = View.VISIBLE }
        }
        else{
            runOnUiThread { textView_sellerItemActivityNoItem.visibility = View.GONE }
        }
        //viewAdapter = RecyclerCallViewAdapter(dataset)
        runOnUiThread {
            (recyclerView.adapter as RecyclerSellerItemAdapter).dataset = dataset
            recyclerView.adapter.notifyDataSetChanged()
        }
    }
}
