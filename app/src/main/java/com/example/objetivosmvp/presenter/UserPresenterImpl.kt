package com.example.objetivosmvp.presenter

import com.example.objetivosmvp.model.UserCache
import com.example.objetivosmvp.view.UserView

class UserPresenterImpl(private val view: UserView, private val model: UserCache) : UserPresenter {
    override fun loadUsers() {
        val users = model.getUsers()
        view.showUsers(users)
    }

    override fun addUser(firstName: String, lastName: String) {
        val user = model.addUser(firstName, lastName)
        view.showUserAdded(user)
    }

    override fun updateUser(id: Long, firstName: String, lastName: String) {
        val success = model.updateUser(id, firstName, lastName)
        if (success) {
            val updatedUser = model.getUsers().find { it.id == id }
            updatedUser?.let { view.showUserUpdated(it) }
        }
    }

    override fun deleteUser(id: Long) {
        val success = model.deleteUser(id)
        if (success) {
            view.showUserDeleted(id)
        }
    }

    override fun deleteAllUsers() {
        model.deleteAllUsers()
        view.showAllUsersDeleted()
    }
}
