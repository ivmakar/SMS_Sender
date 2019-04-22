package ru.example.ivan.smssender.ui.screens.group


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import dagger.android.support.DaggerFragment
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.FragmentGroupBinding
import ru.example.ivan.smssender.ui.rvadapters.GroupRecyclerViewAdapter
import ru.example.ivan.smssender.ui.uimodels.Group

import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_HAS_CONTACTS = "arg_has_contacts"
/**
 * A simple [Fragment] subclass.
 *
 */
class GroupFragment : DaggerFragment(), GroupRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: FragmentGroupBinding
    private val groupRecyclerViewAdapter = GroupRecyclerViewAdapter(arrayListOf(), this)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_group, container, false)
        var view = binding.root

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(GroupViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.groupRv.layoutManager = LinearLayoutManager(activity)
        binding.groupRv.adapter = groupRecyclerViewAdapter
        viewModel.groups.observe(this,
            Observer<ArrayList<Group>> { it?.let{ groupRecyclerViewAdapter.replaceData(it)} })

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
        bundle.putString("chainName", viewModel.getGroupByPosition(position)?.groupName)
        bundle.putInt("groupId", viewModel.getGroupByPosition(position)!!.id)
        NavHostFragment.findNavController(this).navigate(R.id.action_groupFragment_to_messagesFragment, bundle)
    }

}
