package com.babob.sporcantam.activity

import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import com.babob.sporcantam.R
import com.babob.sporcantam.adapter.RecyclerCustomerItemAdapter
import com.babob.sporcantam.adapter.RecyclerShoppingCartAdapter
import com.babob.sporcantam.item.Item
import com.babob.sporcantam.utility.AsyncUtil
import com.babob.sporcantam.utility.HttpUtil
import com.babob.sporcantam.utility.JsonUtil
import com.babob.sporcantam.utility.SessionUtil
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import org.w3c.dom.Text
import java.lang.Exception

class ShoppingCartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    lateinit var dataset:ArrayList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        textViewPrice = textView_shoppingCart_price
        textViewTax = textView_shoppingCart_priceShoppingTax

        title = "Shopping Cart"

        dataset = arrayListOf()

        viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerShoppingCartAdapter(dataset, this)


        recyclerView = findViewById<RecyclerView>(R.id.recyclerShoppingCartView).apply {
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
        textViewPrice = textView_shoppingCart_price
        textViewTax = textView_shoppingCart_priceShoppingTax
        resetList()
        AsyncUtil{
            updateList()
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

    }

    fun updateList(){
        dataset = JsonUtil.getItemResponseToList(
                HttpUtil.sendPoststr(
                        "","${getString(R.string.base_url)}/customer/viewCart", SessionUtil.getSessionId(this)!!))

        //viewAdapter = RecyclerCallViewAdapter(dataset)
        runOnUiThread {
            (recyclerView.adapter as RecyclerShoppingCartAdapter).dataset = dataset
            recyclerView.adapter.notifyDataSetChanged()
        }
    }

    companion object {
        var itemList:ArrayList<Item> = ArrayList()
        var totalPrice:Float = 0f
        var textViewPrice: TextView? = null
        var textViewTax: TextView? = null

        fun addToList(item: Item){
            itemList.add(item)
            Log.i("shopping cart", "item added : ${item.item_title}")
            totalPrice+= item.price
            updatePrice()
        }

        fun deleteFromList(item: Item){
            try {
                itemList.remove(item)
                totalPrice-= item.price
                updatePrice()
                Log.i("shopping cart", "item deleted : ${item.item_title}")
            } catch (e:Exception){
                Log.e("shopping cart", "Unable to delete")
            }
        }

        fun resetList(){
            itemList.clear()
            totalPrice = 0f
            updatePrice()
        }

        fun updatePrice(){
            val taxAndShip = ((totalPrice * 0.18) + (itemList.size * 5)).format(2)
            textViewTax!!.text = "$taxAndShip $"
            textViewPrice!!.text = "${(totalPrice + taxAndShip.toFloat()).format(2)} $"
        }

        fun Float.format(digits: Int) = java.lang.String.format("%.${digits}f", this)
        fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)

    }
}
