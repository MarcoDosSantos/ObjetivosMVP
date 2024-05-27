package com.example.objetivosmvp.model

class UserCache {
    private val users = mutableListOf<User>()
    private var nextId = 1L

    fun getUsers(): List<User> = users

    fun addUser(firstName: String, lastName: String): User {
        val user = User(nextId++, firstName, lastName)
        users.add(user)
        return user
    }

    fun updateUser(id: Long, firstName: String, lastName: String): Boolean {
        val user = users.find { it.id == id } ?: return false
        user.firstName = firstName
        user.lastName = lastName
        return true
    }

    fun deleteUser(id: Long): Boolean {
        return users.removeIf { it.id == id }
    }

    fun deleteAllUsers() {
        users.clear()
    }
}
