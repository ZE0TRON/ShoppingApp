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
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.item.Item
import com.babob.sporcantam.utility.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_seller_item_view_update.*
import java.io.IOException
import java.util.*

class SellerItemView_UpdateActivity : AppCompatActivity() {

    var update_save = true
    /*
    true-> view page
    false-> update page
    */
    var isPhotoChecked = false

    private val PICK_IMAGE_REQUEST = 1
    private val STORAGE_PERMISSION_CODE = 123
    private var bitmap: Bitmap? = null
    private var filePath: Uri? = null
    var isadmin:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_item_view_update)


        val item: Item = intent.getSerializableExtra("item") as Item
        isadmin = intent.getSerializableExtra("admin") as Boolean

        initSpinners(item)
        requestStoragePermission()
        fillFields(item)

        button_ItemView_UpdateActivityUpdateSave.setOnClickListener {
            //AsyncUtil{helper(item.uuid)}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            helper(item.uuid)
        }
        button_ItemView_UpdateActivityDelete.setOnClickListener {
            AsyncUtil{ delete(item.uuid) }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
             }

        button_ItemView_UpdatePhoto.setOnClickListener { showFileChooser() }

    }

    fun initSpinners(item:Item){
        val spinnerShippingArray = ArrayList<String>()
        SpinnerHelperUtil.transportationHelper(spinnerShippingArray)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerShippingArray)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val sItems = spinner_ItemView_UpdateShipping
        sItems.adapter = adapter

        sItems.setSelection(spinnerShippingArray.indexOf(item.shipping_info))

        val spinnerCategoryArray = ArrayList<String>()
        SpinnerHelperUtil.categoryHelper(spinnerCategoryArray)

        val adapter2 = ArrayAdapter(this,android.R.layout.simple_spinner_item,spinnerCategoryArray)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val sItems2=spinner_ItemView_UpdateCategory
        sItems2.adapter=adapter2

        sItems2.setSelection(spinnerCategoryArray.indexOf(item.category))

    }

    fun fillFields(item: Item){
        editText_ItemView_UpdateActivityItemTitle.setText(item.item_title)
        editText_ItemView_UpdateActivityDescription.setText(item.description)
        editText_ItemView_UpdateActivityStock.setText(item.stock_count.toString())
        editText_ItemView_UpdateActivityPrice.setText(item.price.toString())
        editText_ItemView_UpdateActivitySeller.setText(item.seller)

        val url = "${getString(R.string.base_url)}/customer/downloadFile/"+item.uuid

        Glide.with(this).load(url).into(imageView_ItemView_UpdatePhoto)

    }

    fun changeEnable() {

        runOnUiThread {
            if (button_ItemView_UpdateActivityUpdateSave.text == "Update")
                button_ItemView_UpdateActivityUpdateSave.text = "Save"
            else
                button_ItemView_UpdateActivityUpdateSave.text = "Update"

            button_ItemView_UpdateActivityDelete.isEnabled = !button_ItemView_UpdateActivityDelete.isEnabled
            if (button_ItemView_UpdatePhoto.visibility == View.VISIBLE)
                button_ItemView_UpdatePhoto.visibility = View.INVISIBLE
            else
                button_ItemView_UpdatePhoto.visibility = View.VISIBLE

            editText_ItemView_UpdateActivityItemTitle.isEnabled = !editText_ItemView_UpdateActivityItemTitle.isEnabled
            editText_ItemView_UpdateActivityDescription.isEnabled = !editText_ItemView_UpdateActivityDescription.isEnabled
            editText_ItemView_UpdateActivityStock.isEnabled = !editText_ItemView_UpdateActivityStock.isEnabled
            editText_ItemView_UpdateActivityPrice.isEnabled = !editText_ItemView_UpdateActivityPrice.isEnabled
            spinner_ItemView_UpdateShipping.isEnabled != spinner_ItemView_UpdateShipping.isEnabled
            spinner_ItemView_UpdateCategory.isEnabled != spinner_ItemView_UpdateCategory.isEnabled

        }
    }


    fun helper(uuid:String){
        Log.d("UpdateItem", uuid)
        if(update_save){
            changeEnable()
            this.update_save =!this.update_save
        }
        else{

            if( editText_ItemView_UpdateActivityItemTitle.text.isEmpty() or
                editText_ItemView_UpdateActivityDescription.text.isEmpty() or
                editText_ItemView_UpdateActivityStock.text.isEmpty() or
                editText_ItemView_UpdateActivityPrice.text.isEmpty())
            {

                Toast.makeText(this,"There is/are empty field(s)", Toast.LENGTH_SHORT).show()
                return

            }

            val tit=editText_ItemView_UpdateActivityItemTitle.text.toString()
            val des=editText_ItemView_UpdateActivityDescription.text.toString()
            val sto=editText_ItemView_UpdateActivityStock.text.toString().toInt()
            val pri=editText_ItemView_UpdateActivityPrice.text.toString().toFloat()
            val shi=spinner_ItemView_UpdateShipping.selectedItem.toString()
            val cat=spinner_ItemView_UpdateCategory.selectedItem.toString()


            AsyncUtil{
                val responseList = CheckerUtil.responseListChecker(JsonUtil.generalServerResponseToList(updateItemRequest(tit,des,sto,pri,shi,cat,uuid)))
                if(isPhotoChecked)
                    MultiHttpSenderUtil.uploadToServer(getPath(this.filePath!!), this,uuid)

                runOnUiThread{
                    Toast.makeText(this,responseList[1],Toast.LENGTH_SHORT).show()
                }
                if(responseList[0] == "true"){
                    changeEnable()
                    this.update_save =!this.update_save
                    finish()
                }

            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

        }

    }

    fun delete(uuid: String){


        AsyncUtil{
            val responseList = CheckerUtil.responseListChecker(JsonUtil.generalServerResponseToList(deleteItemRequest(uuid)))

            runOnUiThread{
                Toast.makeText(this,responseList[1],Toast.LENGTH_SHORT).show()
            }
            if(responseList[0] == "true"){
                //which will activity open?
                finish()
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

    }


    fun deleteItemRequest(uu:String):String{
        if(isadmin){
            return HttpUtil.sendPoststr(UrlParamUtil.UUIDtoUrlParam(uu),"${getString(R.string.base_url)}/admin/item/delete",SessionUtil.getSessionId(this)!!)
        }
        return HttpUtil.sendPoststr("", "${getString(R.string.base_url)}/seller/my-items/"+uu+"/delete", SessionUtil.getSessionId(this)!!)
    }

    fun updateItemRequest(t:String,d:String,st:Int,p:Float,sh:String,c:String,uu:String):String{
        if(isadmin){
            return HttpUtil.sendPoststr(UrlParamUtil.itemtoUrlParam(t,d,st,p,sh,c,uu),"${getString(R.string.base_url)}/admin/item/update",SessionUtil.getSessionId(this)!!)
        }
        return HttpUtil.sendPoststr(UrlParamUtil.updateItemToUrlParam(t,p,d,sh,st,c), "${getString(R.string.base_url)}/seller/my-items/"+uu+"/update", SessionUtil.getSessionId(this)!!)
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
                imageView_ItemView_UpdatePhoto.setImageBitmap(bitmap)
                isPhotoChecked=true
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



}
