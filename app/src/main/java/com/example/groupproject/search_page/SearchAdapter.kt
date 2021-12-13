package com.example.groupproject.search_page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.groupproject.R
import com.example.groupproject.profile_page.User
import kotlinx.android.synthetic.main.row_items.view.*

class SearchAdapter(var clickedItem: ClickedItem) : RecyclerView.Adapter<SearchAdapter.MyViewHolder>(),
    Filterable {

    var userList = ArrayList<User>()
    var userListFilter = ArrayList<User>()

    fun setData(userList: ArrayList<User>) {
        this.userList = userList
        this.userListFilter = userList
        notifyDataSetChanged()
    }

    interface ClickedItem{
        fun clickedItem(user: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_items, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var user = userList[position]

        holder.username.text = user.username
        holder.fullName.text = (user.fName + " " + user.lName)

        holder.itemView.setOnClickListener{
            clickedItem.clickedItem(user)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username = itemView.txtUsername
        var fullName = itemView.txtFullName
    }

    override fun getFilter(): Filter{
        return object: Filter(){
            override fun performFiltering(charSequence: CharSequence): FilterResults{
                var filterResults = FilterResults()
                var user = ArrayList<User>()
                if(charSequence == null || charSequence.isEmpty()){
                    filterResults.count = 0
                    filterResults.values = user
                }else{
                    var searchChr: String = charSequence.toString().toLowerCase()
                    user.clear()
                    for(users in userListFilter){
                        if(users.username.toLowerCase().contains(searchChr) || users.fName.toLowerCase().contains(searchChr) || users.lName.toLowerCase().contains(searchChr)){
                            user.add(users)
                        }
                    }

                    filterResults.count = user.size
                    filterResults.values = user
                }
                return filterResults
            }
            override fun publishResults(p0: CharSequence?, p1: FilterResults?){
                userList = p1!!.values as ArrayList<User>
                notifyDataSetChanged()
            }
        }
    }
}