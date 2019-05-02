package ru.example.ivan.smssender.ui.screens.template


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
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
import ru.example.ivan.smssender.databinding.FragmentTemplateBinding
import ru.example.ivan.smssender.ui.rvadapters.TemplateRecyclerViewAdapter
import ru.example.ivan.smssender.ui.uimodels.Template
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

        binding.messageRv.layoutManager = LinearLayoutManager(activity)
        binding.messageRv.adapter = templateRecyclerViewAdapter
        viewModel.templates.observe(this,
            Observer<ArrayList<Template>> { it?.let { templateRecyclerViewAdapter.replaceData(it) } })

        return view
    }

    override fun onItemClick(position: Int) {
        //TODO: return template text
    }
}