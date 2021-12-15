package com.example.groupproject.search_page
/**
 * Author: Cameron Triplett
 * Date: December 1, 2021
 * Modified: December 13, 2021
 */
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.groupproject.R
import com.example.groupproject.profile_page.User
import kotlinx.android.synthetic.main.row_items.view.*

/**
 * This class sets the adapter to the recycler veiw in user search
 * @param clickedItem: user being clicked on
 */
class SearchAdapter(var clickedItem: ClickedItem) : RecyclerView.Adapter<SearchAdapter.MyViewHolder>(),
    Filterable {

    var userList = ArrayList<User>()
    var userListFilter = ArrayList<User>()

    /**
     * This method sets the data in the recycler view to the users list
     * @param userList: the list of users
     */
    fun setData(userList: ArrayList<User>) {
        this.userList = userList
        this.userListFilter = userList
        notifyDataSetChanged()
    }

    /**
     * This interface set the clicked item and action
     */
    interface ClickedItem{
        fun clickedItem(user: User)
    }

    /**
     * This method creates the viewholder for the recycler view
     * @param parent: the parent view where the recycler view is going
     * @param viewType: the type of view that is being passed in
     * @return: the view holder with the layout inflater
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_items, parent, false)
        )
    }

    /**
     * This method returns the size of the user list
     * @return user list size
     */
    override fun getItemCount(): Int {
        return userList.size
    }

    /**
     * This method set the viewholder binding
     * @param holder: the view holder
     * @param position: where we are for an index in the list
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var user = userList[position]

        //Set all available attributes to the list item
        holder.username.text = user.username
        holder.fullName.text = (user.fName + " " + user.lName)

        //Set on click listener for the user
        holder.itemView.setOnClickListener{
            clickedItem.clickedItem(user)
        }
    }

    /**
     * This class is the view holder for the recycler view
     * @param itemView: view in the recycler view
     */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username = itemView.txtUsername
        var fullName = itemView.txtFullName
    }

    /**
     * This method filters the list and creates a new list based on the filter
     * It then updates the screen with the updated list
     * @return: returns the filter object
     */
    override fun getFilter(): Filter{
        return object: Filter(){
            /**
             * This method perfoms the filtering of the list
             * @param charSequence: the sequence of characters filtering the list
             * @return: the filtered results
             */
            override fun performFiltering(charSequence: CharSequence): FilterResults{
                var filterResults = FilterResults()
                var user = ArrayList<User>()
                //if no filter set whole list
                if(charSequence == null || charSequence.isEmpty()){
                    filterResults.count = 0
                    filterResults.values = user
                }
                //if filtered set filtered list
                else{
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

            /**
             * This method sets the results of the filter
             * @param p0: the character sequence to filter the list by
             * @param p1: the filtered list of results
             */
            override fun publishResults(p0: CharSequence?, p1: FilterResults?){
                userList = p1!!.values as ArrayList<User>
                notifyDataSetChanged()
            }
        }
    }
}