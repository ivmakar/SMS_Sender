package ru.example.ivan.smssender.ui.screens.new_template


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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