package com.ashwini.imgurgallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class ImgurImagesActivity : AppCompatActivity() {
    private var layoutManager: GridLayoutManager? = null
    private var obj_adapter: ImgurImageAdapter? = null
    private var recyclerView: RecyclerView?=null
    var arrayList_details:ArrayList<ImgurModel> = ArrayList();

    val client = OkHttpClient()
    var query:String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imgur_images)
        query= intent.getStringExtra("Name")
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        layoutManager = GridLayoutManager(this, 2)
        recyclerView!!?.layoutManager = layoutManager
        obj_adapter = ImgurImageAdapter(arrayList_details, layoutManager!!)
        recyclerView!!?.adapter = obj_adapter
        run(query!!)
    }
    fun run(query: String) {

        val IMGUR_CLIENT_ID = "a87f1825654f1c9"
        val request = Request.Builder()
            .cacheControl(CacheControl.Builder().noCache().build())
            .header("Authorization", "Client-ID $IMGUR_CLIENT_ID")
            .url("https://api.imgur.com/3/gallery/search/top/month/1?q="+query)
            .get()
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                var str_response = response.body!!.string()
                //creating json object
                val json_contact: JSONObject = JSONObject(str_response)
                //creating json array
                var jsonarray_data: JSONArray = json_contact.getJSONArray("data")
                var size: Int = jsonarray_data.length()

                for (i in 0..size-1) {
                    var json_objectdetail: JSONObject = jsonarray_data.getJSONObject(i)
                    var model = ImgurModel()
                    model.imgId = json_objectdetail.getString("id")
                    model.title = json_objectdetail.getString("title")
                    model.post_date = json_objectdetail.getString("datetime")

                    if(json_objectdetail.getInt("images_count").equals(null))
                        model.image_num = 0
                    else
                        model.image_num = json_objectdetail.getInt("images_count")

                    var jsonarray_data: JSONArray = json_objectdetail.getJSONArray("images")
                    var imagesize: Int = jsonarray_data.length()
                    for (j in 0 .. imagesize-1){
                    var  json_imgobjectdetail:JSONObject = jsonarray_data.getJSONObject(0)

                    model.imageval = json_imgobjectdetail!!.getString("link")
                    }
                    arrayList_details.add(model)
                }

                runOnUiThread {
                    //stuff that updates ui
                    val sortedarlist = arrayList_details.sortedWith(
                        compareByDescending(ImgurModel::post_date)
                    )
                     val obj_adapter: ImgurImageAdapter
                    obj_adapter = ImgurImageAdapter(sortedarlist, layoutManager!!)
                    recyclerView!!?.adapter = obj_adapter
                }

            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_type, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.change_layout -> {
                if (layoutManager?.spanCount == 1) {
                    layoutManager?.spanCount = 3
                    item.title = "list"
                    recyclerView!!?.setLayoutManager(GridLayoutManager(this, 3))

                } else {
                    layoutManager?.spanCount = 1
                    item.title = "grid"
                    recyclerView!!?.setLayoutManager(LinearLayoutManager(this))
                }

                obj_adapter?.notifyItemRangeChanged(0, obj_adapter?.itemCount ?: 0)
            }
        }
        return super.onOptionsItemSelected(item)

    }

}



