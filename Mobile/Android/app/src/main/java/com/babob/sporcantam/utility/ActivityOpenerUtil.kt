package com.babob.sporcantam.utility

import android.content.Context
import android.content.Intent
import com.babob.sporcantam.activity.*
import com.babob.sporcantam.activity.admin.*
import com.babob.sporcantam.activity.customer.AddBalanceActivity
import com.babob.sporcantam.activity.customer.CustomerMainActivity
import com.babob.sporcantam.activity.customer.ShoppingCartActivity
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

        fun openMainPageActivity(context: Context, type: Int){
            context.startActivity(Intent(context, MainPageActivity::class.java).putExtra("type", type))
        }

        fun openItemCreateActivity(context: Context){
            context.startActivity(Intent(context, ItemCreateActivity::class.java))
        }

        fun openSellerItemListActivity(context: Context){
            context.startActivity(Intent(context, SellerItemListActivity::class.java))
        }

        fun openItemView_UpdateActivity(context: Context,item: Item){
            context.startActivity(Intent(context,SellerItemView_UpdateActivity::class.java).putExtra("item",item))
        }

        fun openCustomerMainActivitty(context: Context){
            context.startActivity(Intent(context, CustomerMainActivity::class.java))
        }

        fun openViewItemActivity(context: Context,item: Item){
            context.startActivity(Intent(context,CustomerViewItemActivity::class.java).putExtra("item",item))
        }

        fun openShoppingCartActivity(context: Context){
            context.startActivity(Intent(context, ShoppingCartActivity::class.java))
        }

        fun openUpdateCustomerInfoActivity(context: Context){
            context.startActivity(Intent(context, UpdateCustomerInfoActivity::class.java))
        }

        fun openUpdateSellerInfoActivity(context: Context){
            context.startActivity(Intent(context, updateSellerInfoActivity::class.java))
        }

        fun openManipulateCustomersActivity(context: Context){
            context.startActivity(Intent(context, ManipulateCustomersActivity::class.java))
        }

        fun openManipulateItemsActivity(context: Context){
            context.startActivity(Intent(context, ManipulateItemsActivity::class.java))
        }

        fun openManipulateOrdersActivity(context: Context){
            context.startActivity(Intent(context, ManipulateOrdersActivity::class.java))
        }

        fun openManipulateSellersActivity(context: Context){
            context.startActivity(Intent(context, ManipulateSellersActivity::class.java))
        }

        fun openAdminNavMainPage(context: Context){
            context.startActivity(Intent(context, AdminNavMainPage::class.java))
        }

        fun openAddBalanceActivity(context: Context){
            context.startActivity(Intent(context, AddBalanceActivity::class.java))
        }

        fun openViewHistoryActivity(context: Context){
            context.startActivity(Intent(context, ViewHistoryActivity::class.java))
        }

    }
}