package com.nandaadisaputra.simplemodularization.core.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nandaadisaputra.simplemodularization.core.R
import com.nandaadisaputra.simplemodularization.core.data.model.Users
import com.nandaadisaputra.simplemodularization.core.databinding.ItemUsersBinding

class UsersAdapter: ListAdapter<Users, UsersAdapter.ItemViewHolder>(DiffUtilCallback()) {

    private var onItemClick: ((Users) -> Unit)? = null

    fun setOnClickItem(onClickItem: (Users) -> Unit) {
        this.onItemClick = onClickItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = DataBindingUtil.inflate<ItemUsersBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_users,
            parent,
            false
        )
        return ItemViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { users ->
            holder.bind(users)
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(users)
            }
        }
    }

    inner class ItemViewHolder(private val binding: ItemUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(users: Users) {
           /*users diambil dari <variable name="users" yang berada di layout item_users.xml*/
            binding.users = users
            binding.executePendingBindings()
        }
    }

    class DiffUtilCallback<T: Any> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return newItem === oldItem
        }
    }
}