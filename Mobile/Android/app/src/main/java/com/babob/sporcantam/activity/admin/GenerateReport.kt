package com.babob.sporcantam.activity.admin

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.babob.sporcantam.R
import com.babob.sporcantam.utility.AsyncUtil
import com.babob.sporcantam.utility.HttpUtil
import com.babob.sporcantam.utility.JsonUtil
import com.babob.sporcantam.utility.SessionUtil
import kotlinx.android.synthetic.main.activity_generate_report.*

class GenerateReport : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_report)


        AsyncUtil{
            val response = JsonUtil.getGenerateSaleReport(
                    HttpUtil.sendPoststr("","${getString(R.string.base_url)}/admin/generateSaleReport",SessionUtil.getSessionId(this)!!)
            )

            for (key in response.keys){
                runOnUiThread {
                    when (key) {
                        "running" -> textView_generate_report_running.text = response[key]
                        "clothes" -> textView_generate_report_clothes.text = response[key]
                        "fitness" -> textView_generate_report_fitness.text = response[key]
                        "hiking" -> textView_generate_report_hiking.text = response[key]
                        "ski" -> textView_generate_report_ski.text = response[key]
                        "snowboard" -> textView_generate_report_snowboard.text = response[key]
                        "soccer" -> textView_generate_report_soccer.text = response[key]
                        "basketball" -> textView_generate_report_basketball.text = response[key]
                        "swimming" -> textView_generate_report_swimming.text = response[key]
                        "cycling" -> textView_generate_report_cycling.text = response[key]
                        "tennis" -> textView_generate_report_tennis.text = response[key]
                    }
                }
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

        fillFields()
    }

    fun fillFields(){

    }

}
