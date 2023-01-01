package com.ashwini.imgurgallery

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var searchRV: RecyclerView
    lateinit var searchRVAdapter: SearchviewAdapter
    lateinit var searchList: ArrayList<SearchModel>
    private val client = OkHttpClient()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // on below line we are initializing our views with their ids.
        searchRV = findViewById(R.id.recycler_view)
        
        // on below line we are initializing our list
        searchList = ArrayList()
        
        // on below line we are initializing our adapter
        searchRVAdapter = SearchviewAdapter(searchList)

        runimgursearch()

        searchRVAdapter.notifyDataSetChanged()
        
        }

    private fun runimgursearch() {

        val IMGUR_CLIENT_ID = "a87f1825654f1c9"
        val request = Request.Builder()
            .cacheControl(CacheControl.Builder().noCache().build())
            .header("Authorization", "Client-ID $IMGUR_CLIENT_ID")
            .url("https://api.imgur.com/3/tags")
            .get()
            .build()



        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val str_response = response.body!!.string()
                //creating json object
                val jsonobject: JSONObject = JSONObject(str_response)
                //creating json array
                val json_dataobject: JSONObject = jsonobject.getJSONObject("data")

                //creating json array
                    val jsonarray_tags: JSONArray = json_dataobject.getJSONArray("tags")
                    val size: Int = jsonarray_tags!!.length()
                    for (i in 0..size-1) {
                        //creating json object
                        val objectdetail: JSONObject = jsonarray_tags.getJSONObject(i)
                        var model = SearchModel()
                        model.setnames(objectdetail.getString("name"))
                        model.setdisplay_name(objectdetail.getString("display_name"))
                        searchList.add(model)
                    }

                    runOnUiThread {
                    // on below line we are initializing our adapter
                    searchRVAdapter = SearchviewAdapter(searchList)

                    // on below line we are setting adapter to our recycler view.
                    searchRV.adapter = searchRVAdapter
                }

            }
        })
    }


    // calling on create option menu
    // layout to inflate our menu file.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // below line is to get our inflater
        val inflater = menuInflater
        
        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.menu_main, menu)
        
        // below line is to get our menu item.
        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)
        
        // getting search view of our item.
        val searchView: SearchView = searchItem.actionView as SearchView
        
        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
        android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
            return false
            }
            
            override fun onQueryTextChange(msg: String): Boolean {
            // inside on query text change method we are
            // calling a method to filter our recycler view.
            filter(msg)
            return false
            }
            })
        return true
        }
    
    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<SearchModel> = ArrayList()
        
        // running a for loop to compare elements.
        for (item in searchList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.display_name!!.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))) {
            // if the item is matched we are
            // adding it to our filtered list.
            filteredlist.add(item)
            }
            }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
            } else {
            // at last we are passing that filtered
            // list to our adapter class.
            searchRVAdapter.filterList(filteredlist)
            }
        }
    
}
