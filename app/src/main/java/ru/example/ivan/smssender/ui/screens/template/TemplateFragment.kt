package ru.example.ivan.smssender.ui.screens.template


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerFragment

import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.FragmentTemplateBinding
import ru.example.ivan.smssender.ui.rvadapters.TemplateRecyclerViewAdapter
import ru.example.ivan.smssender.data.dbmodels.Template
import ru.example.ivan.smssender.utility.Constants
import ru.example.ivan.smssender.utility.navigation.OnBackPressedListener
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class TemplateFragment : DaggerFragment(), TemplateRecyclerViewAdapter.OnItemClickListener, OnBackPressedListener {

    private lateinit var binding: FragmentTemplateBinding
    private val templateRecyclerViewAdapter = TemplateRecyclerViewAdapter(arrayListOf(), this)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var isSelectionFragment = false

    private fun setupUi() {
        arguments?.getBoolean(Constants.KEY_IS_SELECTION_FRAGMENT)?.let { it -> isSelectionFragment = it }

        val bottomNavigationView = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.visibility = if (isSelectionFragment) View.GONE else View.VISIBLE

        activity?.let { it.title = "Шаблоны" }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_template, container, false)
        var view = binding.root

        setupUi()

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(TemplateViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        viewModel.loadTemplates()

        binding.messageRv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        binding.messageRv.adapter = templateRecyclerViewAdapter
        viewModel.templates.observe(this,
            Observer<ArrayList<Template>> { it?.let { templateRecyclerViewAdapter.replaceData(it) } })

        var fab = view.findViewById<FloatingActionButton>(R.id.fab_template)
        fab.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_templateFragment_to_newTemplateFragment)
        }

        return view
    }

    override fun onItemClick(position: Int) {

        if (!isSelectionFragment) {
            return
        }

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(TemplateViewModel::class.java)
        val localBroadcastIntent = Intent(Constants.SELECTED_TEMPLATE)
        val bundle = Bundle()
        bundle.putString(Constants.KEY_TEMPLATE, viewModel.getTemplateTextByPosition(position))
        localBroadcastIntent.putExtras(bundle)
        LocalBroadcastManager.getInstance(activity!!).sendBroadcast(localBroadcastIntent)

        NavHostFragment.findNavController(this).popBackStack()
    }

    override fun onBackPressed() {
        val bottomNavigationView = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.action_messages
    }
}