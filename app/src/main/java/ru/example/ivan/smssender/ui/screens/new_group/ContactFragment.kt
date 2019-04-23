package ru.example.ivan.smssender.ui.screens.new_group


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import dagger.android.support.DaggerFragment
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.FragmentContactBinding
import ru.example.ivan.smssender.ui.rvadapters.ContactRecyclerViewAdapter
import ru.example.ivan.smssender.ui.uimodels.Contact

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


        binding.contactRv.layoutManager = LinearLayoutManager(activity)
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
