package com.example.objetivosmvp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.objetivosmvp.databinding.ItemUserBinding
import com.example.objetivosmvp.model.User
import com.example.objetivosmvp.presenter.UserPresenter


class UserAdapter(
    private val presenter: UserPresenter,
    private val onUserSelected: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val users = mutableListOf<User>()
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.binding.textViewName.text = "${user.firstName} ${user.lastName}"
        holder.binding.radioButtonSelect.isChecked = position == selectedPosition

        holder.binding.radioButtonSelect.setOnClickListener {
            if (selectedPosition != RecyclerView.NO_POSITION) {
                notifyItemChanged(selectedPosition)
            }
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
            onUserSelected(user)
        }

        holder.binding.buttonDelete.setOnClickListener {
            presenter.deleteUser(user.id)
        }
    }

    override fun getItemCount(): Int = users.size

    fun setUsers(users: List<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun addUser(user: User) {
        users.add(user)
        notifyItemInserted(users.size - 1)
    }

    fun updateUser(user: User) {
        val index = users.indexOfFirst { it.id == user.id }
        if (index != -1) {
            users[index] = user
            notifyItemChanged(index)
        }
    }

    fun deleteUser(userId: Long) {
        val index = users.indexOfFirst { it.id == userId }
        if (index != -1) {
            users.removeAt(index)
            if (index == selectedPosition) {
                selectedPosition = RecyclerView.NO_POSITION
            }
            notifyItemRemoved(index)
        }
    }

    fun clearUsers() {
        users.clear()
        selectedPosition = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }

    fun clearSelection() {
        if (selectedPosition != RecyclerView.NO_POSITION) {
            val previousSelectedPosition = selectedPosition
            selectedPosition = RecyclerView.NO_POSITION
            notifyItemChanged(previousSelectedPosition)
        }
    }
}

