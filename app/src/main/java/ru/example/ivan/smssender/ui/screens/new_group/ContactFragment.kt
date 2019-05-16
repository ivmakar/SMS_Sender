package ru.example.ivan.smssender.ui.screens.new_group


import android.Manifest
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import dagger.android.support.DaggerFragment
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.FragmentContactBinding
import ru.example.ivan.smssender.ui.rvadapters.ContactRecyclerViewAdapter
import ru.example.ivan.smssender.ui.uimodels.Contact
import ru.example.ivan.smssender.utility.Constants

import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class ContactFragment : DaggerFragment(), ContactRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: FragmentContactBinding
    private val contactRecyclerViewAdapter = ContactRecyclerViewAdapter(arrayListOf(), this)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false)
        var view = binding.root

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewGroupViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        val readContactsPermission = context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) }
        if (readContactsPermission == PackageManager.PERMISSION_DENIED) {
            requestReadContactsPermission()
        } else {
            readContacts()
        }

        binding.contactRv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        binding.contactRv.adapter = contactRecyclerViewAdapter
        viewModel.contacts.observe(this,
            Observer<ArrayList<Contact>> { it?.let{
                contactRecyclerViewAdapter.replaceData(it)
            } })

        var fab = view.findViewById<FloatingActionButton>(R.id.fab_contact)
        fab.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        return view
    }

    private fun readContacts() {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewGroupViewModel::class.java)

        if (viewModel.contacts.value == null) {
            viewModel.loadContacts(activity!!.contentResolver)
            return
        }
        if (viewModel.contacts.value!!.isEmpty()) {
            viewModel.loadContacts(activity!!.contentResolver)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            Constants.REQUEST_CODE_PERMISSION_READ_CONTACTS -> readContacts()
        }
    }

    private fun requestReadContactsPermission() {
        activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(Manifest.permission.READ_CONTACTS),
                Constants.REQUEST_CODE_PERMISSION_READ_CONTACTS)
        }
    }

    override fun onItemClick(position: Int) {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewGroupViewModel::class.java)

        viewModel.selectItemByPosition(position)
    }

}
