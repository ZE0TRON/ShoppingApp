package com.babob.sporcantam.utility

import android.content.Context
import android.content.Intent
import com.babob.sporcantam.activity.*
import com.babob.sporcantam.item.Item

class ActivityOpenerUtil {

    companion object {
        fun openLoginActivity(context: Context){
            context.startActivity(Intent(context, LoginActivity::class.java))
        }

        fun openSignUpActivity(context: Context){
            context.startActivity(Intent(context, SignUpActivity::class.java))
        }

        fun openMainActivity(context: Context){
            context.startActivity(Intent(context, MainActivity::class.java))
        }

        fun openMainPageActivity(context: Context){
            context.startActivity(Intent(context, MainPageActivity::class.java))
        }

        fun openItemCreateActivity(context: Context){
            context.startActivity(Intent(context, ItemCreateActivity::class.java))
        }

        fun openSellerItemListActivity(context: Context){
            context.startActivity(Intent(context, SellerItemListActivity::class.java))
        }

        fun openItemView_UpdateActivity(context: Context,item: Item){
            context.startActivity(Intent(context,ItemView_UpdateActivity::class.java).putExtra("item",item))
        }

    }
}