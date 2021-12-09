package com.locobird00.contactsproject.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.viewModels

import com.locobird00.contactsproject.databinding.MainFragmentBinding
import com.locobird00.contactsproject.Contact
import com.locobird00.contactsproject.R

class MainFragment : Fragment() {

    private var adapter: ContactListAdapter? = null

    companion object {
        fun newInstance() = MainFragment()
    }

    val viewModel: MainViewModel by viewModels()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listenerSetup()
        observerSetup()
        recyclerSetup()

    }

    private fun listenerSetup() {

        binding.addBtn.setOnClickListener {

            val name = binding.nameInput.text.toString()
            val phone = binding.phoneInput.text.toString()

            if (name != "" && phone != "") {
                val contact = Contact(name, phone)
                viewModel.insertContact(contact)
                clearFields()
            } else {
                Toast.makeText(activity,
                    resources.getString(R.string.incomplete_info_string),
                    Toast.LENGTH_LONG).show()
            }
        }

        binding.findBtn.setOnClickListener {
            val name = binding.nameInput.text.toString()

            if (name.isEmpty())
                Toast.makeText(activity,
                    resources.getString(R.string.find_input),
                    Toast.LENGTH_LONG).show()
            else
                viewModel.findContact(name) }

        binding.ascBtn.setOnClickListener { viewModel.sortAsc() }

        binding.descBtn.setOnClickListener { viewModel.sortDesc() }
    }

    private fun observerSetup() {

        viewModel.getAllContacts()?.observe(viewLifecycleOwner, Observer { contacts ->
            contacts?.let  {
                adapter?.setContactList(it)
            }
        })

        viewModel.getSearchResults().observe(viewLifecycleOwner, Observer { contacts ->

            contacts?.let {

                if (it.isNotEmpty()) {
                    adapter?.setContactList(it)
                } else
                    Toast.makeText(activity,
                        resources.getString(R.string.cant_find_input),
                        Toast.LENGTH_LONG).show()
            }
        })

        viewModel.sortAsc()?.observe(viewLifecycleOwner, Observer { contacts ->

            contacts?.let {
                adapter?.setContactList(it)
            }
        })

        viewModel.sortDesc()?.observe(viewLifecycleOwner, Observer { contacts ->

            contacts?.let {
                adapter?.setContactList(it)
            }
        })
    }

    private fun recyclerSetup() {
        adapter = ContactListAdapter(R.layout.card_layout)
        binding.contactsRecycler.layoutManager = LinearLayoutManager(context)
        binding.contactsRecycler.adapter = adapter

        adapter!!.settingListener(object: ContactListAdapter.OnItemClickListener {
            override fun onClick(string: String) {
                var itemID: Int = Integer.parseInt(string)
                viewModel.deleteContact(itemID)
            }
        })
    }

    private fun clearFields() {
        binding.nameInput.setText("")
        binding.phoneInput.setText("")
    }
}