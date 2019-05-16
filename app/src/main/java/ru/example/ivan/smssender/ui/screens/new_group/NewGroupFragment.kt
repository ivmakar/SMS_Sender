package ru.example.ivan.smssender.ui.screens.new_group


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.example.ivan.smssender.databinding.FragmentNewGroupBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import dagger.android.support.DaggerFragment
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.ui.rvadapters.NewGroupRecyclerViewAdapter
import ru.example.ivan.smssender.ui.screens.main.MainViewModel
import ru.example.ivan.smssender.ui.uimodels.Contact

import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_HAS_CONTACTS = "arg_has_contacts"
private const val ARG_SEND_CONTACTS = "arg_send_contacts"
private const val ARG_RECEIVE_CONTACTS = "arg_receive_contacts"

/**
 * A simple [Fragment] subclass.
 *
 */
class NewGroupFragment : DaggerFragment(), NewGroupRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: FragmentNewGroupBinding
    private val newGroupRecyclerViewAdapter = NewGroupRecyclerViewAdapter(arrayListOf(), sortedSetOf(), this)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_group, container, false)
        var view = binding.root

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewGroupViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()


        binding.groupRv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        binding.groupRv.adapter = newGroupRecyclerViewAdapter
        viewModel.selectedContacts.observe(this,
            Observer<Set<Int>> { it?.let {
                newGroupRecyclerViewAdapter.replaceData(viewModel.contacts.value!!, it)
            } })



        viewModel.navigateComplete.observe(this, Observer {

            //TODO("save new Group")
            NavHostFragment.findNavController(this).popBackStack()
        })

        viewModel.navigateAddContacts.observe(this, Observer {
            NavHostFragment.findNavController(this).navigate(R.id.action_newGroupFragment_to_contactFragment)
        })

        return view
    }

    override fun onItemClick(position: Int) {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewGroupViewModel::class.java)

        viewModel.deleteItemByPosition(  position)
    }

}
