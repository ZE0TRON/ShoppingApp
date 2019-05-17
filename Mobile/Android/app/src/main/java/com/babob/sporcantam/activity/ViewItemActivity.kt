package com.babob.sporcantam.activity

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.item.Item
import com.babob.sporcantam.utility.*
import kotlinx.android.synthetic.main.activity_view_item.*

class ViewItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)

        title = "View Item"

        val item: Item = intent.getSerializableExtra("item") as Item

        fillFields(item)

        button_viewItemAddToCart.setOnClickListener { addToCart(item) }
    }

    fun fillFields(item:Item){
        textView_viewItemDescription.text = item.description
        textView_viewItemTitle.text = item.item_title
        textView_viewItemPriceNum.text = item.price.toString()
        textView_viewItemSeller.text = item.seller
        textView_viewItemStockCount.text = item.stock_count.toString()

    }

    fun addToCart(item:Item){
        AsyncUtil{
            val response = JsonUtil.generalServerResponseToList(HttpUtil.sendPoststr(
                    UrlParamUtil.itemToAddCartParam(item),
                    "${getString(R.string.base_url)}/customer/addToCart", SessionUtil.getSessionId(this)!!))
            when {
                response.size < 2 -> runOnUiThread { Toast.makeText(this, "Cannot connect to the server", Toast.LENGTH_SHORT).show() }
                response[0] == "true" -> runOnUiThread {
                    Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else -> runOnUiThread { Toast.makeText(this, response[1], Toast.LENGTH_SHORT).show() }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

    }
}
