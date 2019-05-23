package ru.example.ivan.smssender.ui.screens.group


import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.Navigator
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerFragment
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.FragmentGroupBinding
import ru.example.ivan.smssender.ui.rvadapters.GroupRecyclerViewAdapter
import ru.example.ivan.smssender.data.dbmodels.Group
import ru.example.ivan.smssender.utility.Constants
import ru.example.ivan.smssender.utility.navigation.OnBackPressedListener

import javax.inject.Inject

private const val ARG_HAS_CONTACTS = "arg_has_contacts"
/**
 * A simple [Fragment] subclass.
 *
 */
class GroupFragment : DaggerFragment(), GroupRecyclerViewAdapter.OnItemClickListener, OnBackPressedListener {

    private lateinit var binding: FragmentGroupBinding
    private val groupRecyclerViewAdapter = GroupRecyclerViewAdapter(arrayListOf(), this)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var isSelectionFragment = false

    private fun setupUi() {
        arguments?.getBoolean(Constants.KEY_IS_SELECTION_FRAGMENT)?.let { it -> isSelectionFragment = it }

        val bottomNavigationView = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.visibility = if (isSelectionFragment) View.GONE else View.VISIBLE

        activity?.let { it.title = "Группы" }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_group, container, false)
        var view = binding.root

        setupUi()

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(GroupViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.groupRv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        binding.groupRv.adapter = groupRecyclerViewAdapter
        viewModel.groups.observe(this,
            Observer<List<Group>> { it?.let{ groupRecyclerViewAdapter.replaceData(it as ArrayList<Group>)} })

        viewModel.navigateToNewGroup.observe(this, Observer {
            var args = Bundle()
            args.putBoolean(ARG_HAS_CONTACTS, false)
            NavHostFragment.findNavController(this).navigate(R.id.action_groupFragment_to_newGroupFragment, args)
        })

        return view
    }

    override fun onItemClick(position: Int) {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(GroupViewModel::class.java)

        var bundle = Bundle()
        bundle.putLong(Constants.KEY_GROUP_ID, viewModel.getGroupByPosition(position)!!.id!!)
        if (isSelectionFragment) {
            val localBroadcastIntent = Intent(Constants.SELECTED_GROUP)
            localBroadcastIntent.putExtras(bundle)
            LocalBroadcastManager.getInstance(activity!!).sendBroadcast(localBroadcastIntent)

            NavHostFragment.findNavController(this).popBackStack()
        } else {
            NavHostFragment.findNavController(this).navigate(R.id.action_groupFragment_to_messagesFragment, bundle)
        }
    }

    override fun onBackPressed() {
        val bottomNavigationView = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.action_messages
    }


}
