package com.babob.sporcantam.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.utility.*
import kotlinx.android.synthetic.main.activity_item_create.*
import java.io.IOException
import java.util.*



class ItemCreateActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private val STORAGE_PERMISSION_CODE = 123
    private var bitmap: Bitmap? = null
    private var filePath: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.babob.sporcantam.R.layout.activity_item_create)

        initSpinners()
        requestStoragePermission()

        button_ItemCreateAdd.setOnClickListener { addItem() }

        button_ItemCreateAddImage.setOnClickListener { showFileChooser() }
    }

    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imageView_ItemCreateItemImage.setImageBitmap(bitmap)

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    fun getPath(uri: Uri): String {
        var cursor = contentResolver.query(uri, null, null, null, null)
        cursor!!.moveToFirst()
        var document_id = cursor.getString(0)
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
        cursor.close()

        cursor = contentResolver.query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", arrayOf(document_id), null)
        cursor!!.moveToFirst()
        val path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        cursor.close()

        return path
    }

    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show()
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun initSpinners(){
        val spinnerShippingArray = ArrayList<String>()
        SpinnerHelperUtil.transportationHelper(spinnerShippingArray)

        val adapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, spinnerShippingArray)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val sItems = spinner_ItemCreateShippingInfo
        sItems.adapter = adapter

        sItems.setSelection(0)

        val spinnerCategoryArray = ArrayList<String>()
        SpinnerHelperUtil.categoryHelper(spinnerCategoryArray)

        val adapter2 = ArrayAdapter(this,android.R.layout.simple_spinner_item,spinnerCategoryArray)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val sItems2=spinner_ItemCreateCategory
        sItems2.adapter=adapter2

        sItems2.setSelection(0)

    }

    fun createuuid(): String {
        val uuid = UUID.randomUUID()
        return uuid.toString()
    }

    fun addItem(){

        if(editText_ItemCreateItemTitle.text.isEmpty()){
            Toast.makeText(this,"Please enter title of new item", Toast.LENGTH_SHORT).show()
            return
        }
        val itemtitle=editText_ItemCreateItemTitle.text.toString()

        if(editText_ItemCreatePrice.text.isEmpty()){
            Toast.makeText(this,"Please enter price of new item", Toast.LENGTH_SHORT).show()
            return
        }
        val itemprice=editText_ItemCreatePrice.text.toString().toFloat()

        if(editText_ItemCreatDescription.text.isEmpty()){
            Toast.makeText(this,"Please enter description of new item", Toast.LENGTH_SHORT).show()
            return
        }
        val itemdescription=editText_ItemCreatDescription.text.toString()

        val itemshipping = spinner_ItemCreateShippingInfo.selectedItem.toString()

        if(editText_ItemCreateStockCount.text.isEmpty()){
            Toast.makeText(this,"Please enter stock of new item", Toast.LENGTH_SHORT).show()
            return
        }
        val itemstock=editText_ItemCreateStockCount.text.toString().toInt()

        val itemCategory = spinner_ItemCreateCategory.selectedItem.toString()

        val uuid = createuuid()

        //TODO: add image checker
        AsyncUtil{
            val responseList = CheckerUtil.responseListChecker(JsonUtil.generalServerResponseToList(sendNewItemRequest(itemtitle,itemprice,itemdescription,itemshipping,itemstock,itemCategory,uuid)))
            MultiHttpSenderUtil.uploadToServer(getPath(this.filePath!!), this,uuid)
            runOnUiThread{
                Toast.makeText(this,responseList[1],Toast.LENGTH_SHORT).show()
            }
            if(responseList[0] == "true"){
                ActivityOpenerUtil.openSellerItemListActivity(this)
                finish()
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

    }

    fun sendNewItemRequest(item_title: String, price: Float, description: String, shipping_info: String, stock_count: Int,item_category: String, uuid: String):String{
        return HttpUtil.sendPoststr(UrlParamUtil.createItemToUrlParam(item_title,price,description,shipping_info,stock_count,item_category,uuid), "${getString(R.string.base_url)}/item/add/", SessionUtil.getSessionId(this)!!)
    }

}
