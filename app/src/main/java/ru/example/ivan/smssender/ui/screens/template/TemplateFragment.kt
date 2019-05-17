package ru.example.ivan.smssender.ui.screens.template


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import dagger.android.support.DaggerFragment

import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.FragmentTemplateBinding
import ru.example.ivan.smssender.ui.rvadapters.TemplateRecyclerViewAdapter
import ru.example.ivan.smssender.ui.screens.new_template.NewTemplateViewModel
import ru.example.ivan.smssender.ui.uimodels.Template
import ru.example.ivan.smssender.utility.Constants
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class TemplateFragment : DaggerFragment(), TemplateRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: FragmentTemplateBinding
    private val templateRecyclerViewAdapter = TemplateRecyclerViewAdapter(arrayListOf(), this)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_template, container, false)
        var view = binding.root

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
        //TODO: return template text
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(TemplateViewModel::class.java)
        val localBroadcastIntent = Intent(Constants.SELECTED_TEMPLATE)
        val bundle = Bundle()
        bundle.putString(Constants.KEY_TEMPLATE, viewModel.getTemplateTextByPosition(position))
        localBroadcastIntent.putExtras(bundle)
        androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(activity!!).sendBroadcast(localBroadcastIntent)

        NavHostFragment.findNavController(this).popBackStack()
    }
}