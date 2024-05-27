package com.example.objetivosmvp.view

import com.example.objetivosmvp.model.User

interface UserView {
    fun showUsers(users: List<User>)
    fun showUserAdded(user: User)
    fun showUserUpdated(user: User)
    fun showUserDeleted(userId: Long)
    fun showAllUsersDeleted()
}
