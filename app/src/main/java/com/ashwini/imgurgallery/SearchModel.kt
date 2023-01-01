package com.ashwini.imgurgallery

 class SearchModel {
     var id: String? = null
     var name: String? = null
     var display_name : String?= null

     fun SearchModel() {}

     fun SearchModel(id: String, name: String, display_name : String) {
        this.id = id
        this.name = name
        this.display_name = display_name
    }

    fun getid(): String? {
        return id
    }

    fun getname(): String? {
        return name
    }

     fun getdisplay_name(): String?{
         return display_name
     }

}