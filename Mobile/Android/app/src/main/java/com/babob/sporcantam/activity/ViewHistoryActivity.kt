package com.babob.sporcantam.activity

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.babob.sporcantam.R
import com.babob.sporcantam.adapter.RecyclerCustomerViewHistory
import com.babob.sporcantam.item.Item
import com.babob.sporcantam.utility.AsyncUtil
import com.babob.sporcantam.utility.HttpUtil
import com.babob.sporcantam.utility.JsonUtil
import com.babob.sporcantam.utility.SessionUtil

class ViewHistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    lateinit var dataset:ArrayList<Item>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_history)

        title = "History"

        dataset = arrayListOf()

        viewManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
        viewAdapter = RecyclerCustomerViewHistory(dataset, this)


        recyclerView = findViewById<RecyclerView>(R.id.RecyclerView_ViewHistoryItemList).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter


        }

    }

    override fun onResume() {
        super.onResume()
        AsyncUtil{
            updateList()
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

    }

    fun updateList(){
        dataset = JsonUtil.getItemResponseToList(
                HttpUtil.sendPoststr(
                        "","${getString(R.string.base_url)}/customer/showViewHistory", SessionUtil.getSessionId(this)!!))

        runOnUiThread {
            (recyclerView.adapter as RecyclerCustomerViewHistory).dataset = dataset
            recyclerView.adapter.notifyDataSetChanged()
        }
    }

}
