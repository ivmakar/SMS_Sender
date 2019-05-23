package ru.example.ivan.smssender.ui.screens.new_template


import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerFragment

import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.FragmentNewTemplateBinding
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class NewTemplateFragment : DaggerFragment() {

    private lateinit var binding: FragmentNewTemplateBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private fun setupUi() {
        val bottomNavigationView = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.visibility = View.GONE

        activity?.let { it.title = "Новый шаблон" }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_template, container, false)
        var view = binding.root

        setupUi()

        val bottomNavigationView = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.visibility = View.GONE

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewTemplateViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        viewModel.templatesLiveData.observe(this, Observer { viewModel.templates = it })

        var fab = view.findViewById<FloatingActionButton>(R.id.fab_new_template)
        fab.setOnClickListener {
            if (viewModel.isCorrectData()) {
                viewModel.saveTemplate()
                NavHostFragment.findNavController(this).popBackStack()
            } else {
                Toast.makeText(context, viewModel.errMessage, Toast.LENGTH_LONG).show()
            }
        }

        return view
    }
}