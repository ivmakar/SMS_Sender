package ru.example.ivan.smssender.ui.screens.new_message


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import dagger.android.support.DaggerFragment
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.FragmentNewMessageBinding

import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class NewMessageFragment : DaggerFragment() {

    private lateinit var binding: FragmentNewMessageBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_message, container, false)
        var view = binding.root

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewMessageViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        viewModel.navigateComplete.observe(this, Observer {
            NavHostFragment.findNavController(this).navigate(R.id.action_newMessageFragment_to_chainFragment)
        })

        return view
    }


}
