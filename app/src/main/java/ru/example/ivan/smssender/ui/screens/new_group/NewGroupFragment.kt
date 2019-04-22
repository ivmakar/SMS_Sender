package ru.example.ivan.smssender.ui.screens.new_group


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
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
    private val newGroupRecyclerViewAdapter = NewGroupRecyclerViewAdapter(arrayListOf(), this)
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

/*        if (arguments?.getBoolean(ARG_HAS_CONTACTS)!!) {
            var selectedContacts: ArrayList<Contact> =
                arguments?.getSerializable(ARG_RECEIVE_CONTACTS) as ArrayList<Contact>
            viewModel.addContacts(selectedContacts)
        }*/
        activity?.let {
            val activityViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
            activityViewModel.selectedContacts.observe(this,
                Observer<ArrayList<Contact>> { it?.let { viewModel.addContacts(it) } })
        }


        binding.groupRv.layoutManager = LinearLayoutManager(activity)
        binding.groupRv.adapter = newGroupRecyclerViewAdapter
        viewModel.contacts.observe(this,
            Observer<ArrayList<Contact>> { it?.let { newGroupRecyclerViewAdapter.replaceData(it) } })

/*        viewModel.notifyAddContact.observe(this,
            Observer {
                newGroupRecyclerViewAdapter.replaceData(viewModel.contacts)
                if (viewModel.multipleChanging)
                    newGroupRecyclerViewAdapter.notifyItemRangeInserted(viewModel.notifyPositionStart, viewModel.notifyPositionEnd)
                else
                    newGroupRecyclerViewAdapter.notifyItemInserted(viewModel.notifyPositionStart)
//                newGroupRecyclerViewAdapter.notifyDataSetChanged()
            })

        viewModel.notifyRemoveContact.observe(this,
            Observer {
                newGroupRecyclerViewAdapter.replaceData(viewModel.contacts)
                newGroupRecyclerViewAdapter.notifyItemRemoved(viewModel.notifyPositionStart)
            })*/

        viewModel.navigateComplete.observe(this, Observer {
            activity?.let {
                val activityViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
                activityViewModel.selectedContacts.value?.clear()
            }
            //TODO("save new Group")
            NavHostFragment.findNavController(this).popBackStack()
        })

        viewModel.navigateAddContacts.observe(this, Observer {
/*            var args = Bundle()
            args.putSerializable(ARG_SEND_CONTACTS, viewModel.contacts.value)*/
            NavHostFragment.findNavController(this).navigate(R.id.action_newGroupFragment_to_contactFragment)
        })

        return view
    }

/*    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE){
            if (resultCode == DaggerAppCompatActivity.RESULT_OK){
                val viewModel = ViewModelProviders.of(this, viewModelFactory)
                    .get(NewGroupViewModel::class.java)

                var selectedContacts = data!!.extras.getSerializable("selectedContacts") as ArrayList<Contact>
                viewModel.addContacts(selectedContacts)
            }
        }
    }*/

    override fun onItemClick(position: Int) {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewGroupViewModel::class.java)

        viewModel.deleteItemByPosition(position)
    }

}
