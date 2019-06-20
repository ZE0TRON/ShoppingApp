package com.babob.sporcantam.activity.admin

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.babob.sporcantam.R
import com.babob.sporcantam.adapter.RecyclerManipulateSeller
import com.babob.sporcantam.item.Seller
import com.babob.sporcantam.utility.AsyncUtil
import com.babob.sporcantam.utility.HttpUtil
import com.babob.sporcantam.utility.JsonUtil
import com.babob.sporcantam.utility.SessionUtil

class ManipulateSellersActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    lateinit var dataset:ArrayList<Seller>
    var isLogged:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manipulate_sellers)

        isLogged = SessionUtil.isLogged(this)

        title = "Sellers"

        dataset = arrayListOf()

        viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerManipulateSeller(dataset,this)


        recyclerView = findViewById<RecyclerView>(R.id.recyclerView_ActivityManipulateSellers_Sellers).apply {
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

        //TODO: recyclerView'lardaki
        dataset = JsonUtil.getSellerResponseToList(HttpUtil.sendPoststr(
                "","${getString(R.string.base_url)}/admin/sellers",SessionUtil.getSessionId(this)!!))


        runOnUiThread {
            (recyclerView.adapter as RecyclerManipulateSeller).dataset = dataset
            recyclerView.adapter.notifyDataSetChanged()
        }
    }

}
