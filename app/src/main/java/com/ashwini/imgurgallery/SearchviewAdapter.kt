package com.ashwini.imgurgallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class SearchviewAdapter(private var searchList: ArrayList<SearchModel>, ) : RecyclerView.Adapter<SearchviewAdapter.CourseViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchviewAdapter.CourseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return CourseViewHolder(itemView)
    }
        
        //this is function to filter a list
        fun filterList(filterlist: ArrayList<SearchModel>) {
           
            searchList = filterlist
            
            notifyDataSetChanged()
            }
        
        override fun onBindViewHolder(holder: SearchviewAdapter.CourseViewHolder, position: Int) {
            
            holder.name.text= (searchList.get(position).name)
            holder.display_name.text=(searchList.get(position).display_name)

        }

    //returns the count of tags
        override fun getItemCount(): Int {
            
            return searchList.size
            }
        
        class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
           
            var name: TextView = itemView.findViewById(R.id.sname)
            var display_name: TextView = itemView.findViewById(R.id.sdisplay_name)


        }
}