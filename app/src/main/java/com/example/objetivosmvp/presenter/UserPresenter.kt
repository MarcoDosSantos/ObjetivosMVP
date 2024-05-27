package com.example.objetivosmvp.presenter

import com.example.objetivosmvp.model.UserCache
import com.example.objetivosmvp.view.UserView

interface UserPresenter {
    fun loadUsers()
    fun addUser(firstName: String, lastName: String)
    fun updateUser(id: Long, firstName: String, lastName: String)
    fun deleteUser(id: Long)
    fun deleteAllUsers()
}

