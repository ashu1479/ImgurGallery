package com.ashwini.imgurgallery

class ImgurModel {
    var imgId: Int?= null
    var title: String?= null
    var post_date:String?= null
    var image_num:Int?= null
    var imageval:String?= null


    fun getimgId(): Int {
        return imgId!!
    }

    fun setimgId(id: Int) {
        this.imgId = imgId
    }

    fun gettitle(): String {
        return title.toString()
    }

    fun settitle(title: String) {
        this.title = title
    }

    fun getpost_date(): String {
        return post_date.toString()
    }

    fun setpost_date(post_date: String) {
        this.post_date = post_date
    }
    fun getimage_num(): Int {
        return image_num!!
    }

    fun setimage_num(id: Int) {
        this.image_num = image_num
    }

    fun getimageval(): String {
        return imageval.toString()
    }

    fun setimageval(imageval: String) {
        this.imageval = imageval
    }
}