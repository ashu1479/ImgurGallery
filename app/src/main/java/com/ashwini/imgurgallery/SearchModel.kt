package com.ashwini.imgurgallery

 class SearchModel {
      // on below line we are creating a two variable
      // one for course name and other for course image.
      var id: String?= null
      var name: String?= null
      var display_name: String?= null
      var followers: Int?= null
      var total_items: Int?= null
      var background_hash: String?= null

     fun getid(): String {
         return id.toString()
     }

     fun setid(id: String) {
         this.id = name
     }

     fun getnames(): String {
         return name.toString()
     }

     fun setnames(name: String) {
         this.name = name
     }

     fun getdisplay_name(): String {
         return display_name.toString()
     }

     fun setdisplay_name(display_name: String) {
         this.display_name = display_name
     }

     fun getfollowers(): Int {
         return followers!!
     }

     fun setfollowers(followers: Int) {
         this.followers = followers
     }

     fun gettotal_items(): Int {
         return total_items!!
     }

     fun settotal_items(total_items: Int) {
         this.total_items = total_items
     }

     fun getbackground_hash(): String {
         return background_hash.toString()
     }

     fun setbackground_hash(background_hash: String) {
         this.background_hash = background_hash
     }







 }

