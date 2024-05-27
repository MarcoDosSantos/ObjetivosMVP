package com.example.objetivosmvp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.objetivosmvp.databinding.ActivityMainBinding
import com.example.objetivosmvp.model.User
import com.example.objetivosmvp.model.UserCache
import com.example.objetivosmvp.presenter.UserPresenter
import com.example.objetivosmvp.presenter.UserPresenterImpl
import com.example.objetivosmvp.view.adapter.UserAdapter


class MainActivity : AppCompatActivity(), UserView {

    private lateinit var presenter: UserPresenter
    private lateinit var userAdapter: UserAdapter
    private lateinit var binding: ActivityMainBinding
    private var selectedUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = UserPresenterImpl(this, UserCache())
        userAdapter = UserAdapter(presenter) { user ->
            // Callback cuando se selecciona un usuario
            selectedUser = user
            binding.editTextFirstName.setText(user.firstName)
            binding.editTextLastName.setText(user.lastName)
            binding.buttonAddUser.text = "Edit User" // Cambiar el texto del botón
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = userAdapter

        binding.buttonAddUser.setOnClickListener {
            val firstName = binding.editTextFirstName.text.toString()
            val lastName = binding.editTextLastName.text.toString()

            if (selectedUser == null) {
                // Agregar nuevo usuario
                presenter.addUser(firstName, lastName)
            } else {
                // Editar usuario existente
                selectedUser?.let {
                    it.firstName = firstName
                    it.lastName = lastName
                    presenter.updateUser(it.id, it.firstName, it.lastName)
                    selectedUser = null
                    userAdapter.clearSelection()
                    binding.buttonAddUser.text = "Add User" // Restablecer el texto del botón
                }
            }

            binding.editTextFirstName.text.clear()
            binding.editTextLastName.text.clear()
        }

        binding.buttonDeleteAllUsers.setOnClickListener {
            presenter.deleteAllUsers()
        }

        presenter.loadUsers()
    }

    override fun showUsers(users: List<User>) {
        userAdapter.setUsers(users)
    }

    override fun showUserAdded(user: User) {
        userAdapter.addUser(user)
    }

    override fun showUserUpdated(user: User) {
        userAdapter.updateUser(user)
    }

    override fun showUserDeleted(userId: Long) {
        userAdapter.deleteUser(userId)
    }

    override fun showAllUsersDeleted() {
        userAdapter.clearUsers()
    }
}
