package com.example.roomviewmodel003.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomviewmodel003.R
import com.example.roomviewmodel003.data.UserViewModel
import com.example.roomviewmodel003.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private lateinit var bind: FragmentListBinding
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bind = FragmentListBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val adapter = ListAdapter()
        val recycleView = bind.recycleview
        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(requireContext())

        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer {listuser ->
            adapter.setData(listuser)
        })

        bind.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        // Добавим меню
        setHasOptionsMenu(true)

        return bind.root  //inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteAllUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Да") { _, _ ->
            //
            mUserViewModel.deleteAllUser()
            Toast.makeText(requireContext(), "Все записи успешно удалены.", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Нет") { _, _ ->}
        builder.setTitle("Удалить все записи?")
        builder.setMessage("Вы действительно хотите удалить все записи из базы данных?")
        builder.create().show()
    }

}