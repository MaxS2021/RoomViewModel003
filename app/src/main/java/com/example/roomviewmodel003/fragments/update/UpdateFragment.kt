package com.example.roomviewmodel003.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomviewmodel003.R
import com.example.roomviewmodel003.data.User
import com.example.roomviewmodel003.data.UserViewModel
import com.example.roomviewmodel003.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {

    lateinit var bind: FragmentUpdateBinding
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bind = FragmentUpdateBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        bind.updateFirstNameEt.setText(args.currentUser.firstName)
        bind.updateLastNameEt.setText(args.currentUser.lastName)
        bind.updateAgeEt.setText(args.currentUser.age.toString())

        bind.updateBtn.setOnClickListener {
            updateItem()
        }

        // Добавим меню
        setHasOptionsMenu(true)

        return bind.root    //inflater.inflate(R.layout.fragment_update, container, false)
    }

    private fun updateItem() {
        val firstName = bind.updateFirstNameEt.text.toString()
        val lastName = bind.updateLastNameEt.text.toString()
        val age = bind.updateAgeEt.text

        if (inputCheck(firstName, lastName, age)) {
            val user = User(args.currentUser.id, firstName, lastName, age.toString().toInt())
            mUserViewModel.updateUser(user)
            Toast.makeText(requireContext(), "Запись $firstName успешно изменена", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Заполните все поля !", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return firstName.isNotEmpty() && lastName.isNotEmpty() && age.isNotEmpty()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Да") { _, _ ->
            //
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(), "Запись успешно удалена.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("Нет") { _, _ ->}
        builder.setTitle("Удалить запись?")
        builder.setMessage("Вы действительно хотите удалить запись: ${args.currentUser.firstName}?")
        builder.create().show()
    }
}