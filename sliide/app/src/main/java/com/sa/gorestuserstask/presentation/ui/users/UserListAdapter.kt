package com.sa.gorestuserstask.presentation.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sa.gorestuserstask.R
import com.sa.gorestuserstask.databinding.ItemUserDataBinding
import com.sa.gorestuserstask.domain.entity.User

class UserListAdapter(private val listener: Listener) :
    ListAdapter<User, UserListAdapter.UserListViewHolder>(UserListDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder =
        UserListViewHolder(
            listener,
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_user_data, parent, false)
        )


    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserListViewHolder(private val listener: Listener, itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserDataBinding.bind(itemView)

        fun bind(user: User) {
            binding.run {
                userName.text = user.name
                userEmail.text = user.email
                userCreateDate.text = user.createdAt.toString()
                userContent.setOnLongClickListener {
                    listener.onItemLongClick(user)
                    true
                }
            }
        }

    }

    class UserListDiffUtils : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem == newItem
    }

    interface Listener {
        fun onItemLongClick(item: User)
    }
}
