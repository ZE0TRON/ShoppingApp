package com.babob.sporcantam.utility

import android.os.AsyncTask

class AsyncUtil(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        handler()
        return null
    }
}