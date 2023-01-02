# Project Title : ImgurGallery

This application displays the top image of month for all gallery tags.You can search from available gallery tags. The images, title, images posted on and number of images are displayed for selected gallery tag.

## Getting Started

Clone the the github url in android studio . Or Download the zip file and extract. And then open project in Android Studio for testing purpose.

### Prerequisites

Generate Client id and client secrete for Imgur Api. The creation of credentials are emplained on https://apidocs.imgur.com. 

### Installing

#Add Gradle dependency for OkHttp3 
    implementation 'com.squareup.okhttp3:okhttp:4.4.0'
    
  ##  Integration of getting all available gallery tags to  search in recyclerview. 
      Below is the request generated using OkHttp3. 
                val IMGUR_CLIENT_ID = "YOUR_CLIENT_ID"
                val request = Request.Builder()
                        .cacheControl(CacheControl.Builder().noCache().build())
                        .header("Authorization", "Client-ID $IMGUR_CLIENT_ID")
                        .url("https://api.imgur.com/3/tags")
                        .get()
                        .build()
            
      Fetch the JSON response in recyclerview. Create option menu in action bar for search. and implement the filter for search.
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
        
        Add below code in searchviewadapter:
        fun filterList(filterlist: ArrayList<SearchModel>) {
           
            searchList = filterlist
            
            notifyDataSetChanged()
            }
    
    Onclick of tag the new activity will open where tag need to be passed by intent as follows:
                val context: Context = holder.itemView.getContext()
                val intent = Intent(context, ImgurImagesActivity::class.java)
                intent.putExtra("Name",holder.name.text)
                context.startActivity(intent)
    get this tag on ImgurImagesActivity to search the top images for month.
    ption menu added to switch/toggle layout between List and Grid.
    Below is the request generated for search.
    val IMGUR_CLIENT_ID = "YOUR_CLIENT_ID"
        val request = Request.Builder()
            .cacheControl(CacheControl.Builder().noCache().build())
            .header("Authorization", "Client-ID $IMGUR_CLIENT_ID")
            .url("https://api.imgur.com/3/gallery/search/top/month/1?q="+query) //query = Name coming from intent
            .get()
            .build()
            
            Fetch the response and add it into reverse chronological order using following code:
             val sortedarlist = arrayList_details.sortedWith(compareByDescending(ImgurModel::post_date))  / descending order based on post_date.

Below are the screenshots of application:
#searchview
[
![search](https://user-images.githubusercontent.com/29300780/210281535-8f8d70e9-7812-42d3-a29d-2263b04738c8.png)
](url)

#search with edittext
![editable search](https://user-images.githubusercontent.com/29300780/210281663-d763b8f1-3b34-46a4-a286-1c9e5fb6024e.png)

#Toggle to Listview
![grid view](https://user-images.githubusercontent.com/29300780/210281578-08f35482-8f4a-46ce-aba1-a71f7ed00a16.png)

#Toggle to Gridview
![listview](https://user-images.githubusercontent.com/29300780/210281918-b53c463c-c51b-4c50-8841-28cc9755a4c5.png)



*
