package com.example.app_testmobile_suitmedia.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app_testmobile_suitmedia.R
import com.example.app_testmobile_suitmedia.data.response.DataItem

class UserAdapter(private val users: MutableList<DataItem>, private val onUserClicked: (DataItem) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_card, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
        holder.itemView.setOnClickListener { onUserClicked(user) }
    }

    override fun getItemCount(): Int = users.size

    fun addUsers(newUsers: List<DataItem>) {
        val startPosition = users.size
        users.addAll(newUsers)
        notifyItemRangeInserted(startPosition, newUsers.size)
    }

    fun clearUsers() {
        users.clear()
        notifyDataSetChanged()
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatarImageView: ImageView = itemView.findViewById(R.id.icon_History)
        private val firstNameTextView: TextView = itemView.findViewById(R.id.tv_Firstname)
        private val lastNameTextView: TextView = itemView.findViewById(R.id.tv_Lastname)
        private val emailTextView: TextView = itemView.findViewById(R.id.tv_TanggalDetection)

        fun bind(user: DataItem) {
            firstNameTextView.text = user.firstName
            lastNameTextView.text = user.lastName
            emailTextView.text = user.email

            Glide.with(itemView.context)
                .load(user.avatar)
                .into(avatarImageView)
        }
    }
}
