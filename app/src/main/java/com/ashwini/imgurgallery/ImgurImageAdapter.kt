package com.ashwini.imgurgallery

import android.annotation.SuppressLint
import android.content.ContentUris
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.lang.System.load
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class ImgurImageAdapter (private val mItems: List<ImgurModel>, layoutManager: GridLayoutManager) : RecyclerView.Adapter<ImgurImageAdapter.ItemViewHolder?>() {
    private val mLayoutManager: GridLayoutManager

    init {
        mLayoutManager = layoutManager
    }


    override fun getItemCount(): Int {
        return mItems.size;
    }
    override fun getItemViewType(position: Int): Int {
        val spanCount: Int = mLayoutManager.getSpanCount()
        return if (spanCount == SPAN_COUNT_ONE) {
            LIST_VIEW
        } else {
            GRID_VIEW
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: View
        //based on selected menu views will be displayed
        view = if (viewType == LIST_VIEW) {
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false)
        } else {
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false)
        }
        return ItemViewHolder(view, viewType)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        //setting up list item to textviews
        holder.title!!.text=(mItems.get(position).gettitle())
        if((mItems.get(position).getimage_num().toString()).equals(0)){
            holder.image_num!!.visibility = View.GONE
        }else {
            holder.image_num!!.text = (mItems.get(position).getimage_num().toString())
        }
        //setting image to imageview from url
        Glide
            .with(holder.itemView.context)
            .asBitmap()
            .load(mItems.get(position).getimageval())
            .into(holder.iv!!);

        //setting up local date time
        var localtime: String = getDate(mItems.get(position).getpost_date().toLong())
        holder.post_date!!.text=localtime

    }


    @SuppressLint("NewApi")
    private fun getDate(dateLong: Long): String {
        //get current offsetDateTime
        val odt = OffsetDateTime.now(ZoneId.systemDefault())
        //set up current offsetDatetime to zoneOffset
        val zoneOffset = odt.offset
        //get LocalDateTime by calling below method
        val datetime : LocalDateTime = LocalDateTime.ofEpochSecond(dateLong, 1000,zoneOffset)
        //formatting date time in required format
        val dateTimeFormatter:DateTimeFormatter = DateTimeFormatter .ofPattern("dd/MM/yy hh:mm a");
        val formattedDate:String = dateTimeFormatter.format(datetime)
        //returning the formatted Date
        return formattedDate.toString()

    }



    inner class ItemViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        var iv: ImageView? = null
        var title: TextView? = null
        var post_date: TextView?=null
        var image_num: TextView?=null


        init {
            if (viewType == LIST_VIEW) {
                iv = itemView.findViewById<View>(R.id.image_big) as ImageView
                title = itemView.findViewById<View>(R.id.list_title) as TextView
                post_date = itemView.findViewById<View>(R.id.list_post_date) as TextView
                image_num = itemView.findViewById<View>(R.id.list_image_num) as TextView


            } else {
                iv = itemView.findViewById<View>(R.id.image_small) as ImageView
                title = itemView.findViewById<View>(R.id.grid_title) as TextView
                post_date = itemView.findViewById<View>(R.id.grid_post_date) as TextView
                image_num = itemView.findViewById<View>(R.id.grid_image_num) as TextView

            }
        }
    }

    companion object {
        const val SPAN_COUNT_ONE = 1
        const val SPAN_COUNT_THREE = 3
        private const val GRID_VIEW = 1
        private const val LIST_VIEW = 2
    }


}