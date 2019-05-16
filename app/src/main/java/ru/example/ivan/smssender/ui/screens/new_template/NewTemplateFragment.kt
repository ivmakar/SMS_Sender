package ru.example.ivan.smssender.ui.screens.new_template


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
import ru.example.ivan.smssender.databinding.FragmentNewTemplateBinding
import ru.example.ivan.smssender.ui.uimodels.Template
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class NewTemplateFragment : DaggerFragment() {

    private lateinit var binding: FragmentNewTemplateBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_template, container, false)
        var view = binding.root

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewTemplateViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        var fab = view.findViewById<FloatingActionButton>(R.id.fab_new_template)
        fab.setOnClickListener {
            viewModel.saveTemplate()
            NavHostFragment.findNavController(this).popBackStack()
        }

        return view
    }
}