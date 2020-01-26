package com.example.kotlinrssreader

import android.app.ProgressDialog
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinrssreader.Adapter.FeedAdapter
import com.example.kotlinrssreader.Common.HTTPDataHandler
import com.example.kotlinrssreader.Model.RSSObject
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val RSS_link = "https://rss.nytimes.com/services/xml/rss/nyt/Science.xml"
    private val RSS_to_JSON_API = " https://api.rss2json.com/v1/api.json?rss_url="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "MEWS"
        setSupportActionBar(toolbar)

        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager

        loadRSS()
    }

    private fun loadRSS() {
        val loadRSSAsync = object:AsyncTask<String, String, String>() {
            override fun onPostExecute(result: String?) {
                mDialog.dismiss()
                val rssObject: RSSObject
                rssObject = Gson().fromJson<RSSObject>(result, RSSObject::class.java!!)
                val adapter = FeedAdapter(rssObject, baseContext)
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun doInBackground(vararg params: String): String {
                val result: String
                val http = HTTPDataHandler()
                result = http.GetHttpDataHandler(params[0])
                return result
            }

            override fun onPreExecute() {
                mDialog.setMessage("Please wait...")
                mDialog.show()
            }

            internal var mDialog = ProgressDialog(this@MainActivity)
        }

        val url_get_data = StringBuffer(RSS_to_JSON_API)
        url_get_data.append(RSS_link)
        loadRSSAsync.execute(url_get_data.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_refresh)
            loadRSS()
        return true
    }
}
