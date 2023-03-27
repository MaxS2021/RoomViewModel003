package com.example.roomviewmodel003.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.roomviewmodel003.R
import com.example.roomviewmodel003.data.User

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var userList = emptyList<User>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = userList[position]
        val id_txt = holder.itemView.findViewById<TextView>(R.id.id_txt)
        val firstName_txt = holder.itemView.findViewById<TextView>(R.id.firstName_txt)
        val lastName_txt = holder.itemView.findViewById<TextView>(R.id.lastName_txt)
        val age_txt = holder.itemView.findViewById<TextView>(R.id.age_txt)
        val rowLayout = holder.itemView.findViewById<ConstraintLayout>(R.id.rowLayout)

        id_txt.text = currentUser.id.toString()
        firstName_txt.text = currentUser.firstName
        lastName_txt.text = currentUser.lastName
        age_txt.text = currentUser.age.toString()

        rowLayout.setOnClickListener {

        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(user: List<User>) {
        this.userList = user
        notifyDataSetChanged()
    }

}