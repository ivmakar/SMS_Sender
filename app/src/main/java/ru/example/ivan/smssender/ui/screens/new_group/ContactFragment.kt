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
import com.google.android.material.bottomnavigation.BottomNavigationView
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

    private fun setupUi() {
        val bottomNavigationView = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.visibility = View.GONE

        activity?.let { it.title = "Новая группа" }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false)
        var view = binding.root

        setupUi()

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewGroupViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

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

    override fun onItemClick(position: Int) {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewGroupViewModel::class.java)

        viewModel.selectItemByPosition(position)
    }

}
