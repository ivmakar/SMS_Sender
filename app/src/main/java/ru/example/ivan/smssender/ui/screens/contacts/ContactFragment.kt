package ru.example.ivan.smssender.ui.screens.contacts


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
import android.widget.Toast
import dagger.android.support.DaggerFragment
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.FragmentContactBinding
import ru.example.ivan.smssender.ui.rvadapters.ContactRecyclerViewAdapter
import ru.example.ivan.smssender.ui.uimodels.Contact

import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ContactFragment : DaggerFragment(), ContactRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: FragmentContactBinding
    private val contactRecyclerViewAdapter = ContactRecyclerViewAdapter(arrayListOf(), this)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false)
        var view = binding.root

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ContactViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.contactRv.layoutManager = LinearLayoutManager(activity)
        binding.contactRv.adapter = contactRecyclerViewAdapter
        viewModel.contacts.observe(this,
            Observer<ArrayList<Contact>> { it?.let{ contactRecyclerViewAdapter.replaceData(it)} })

        viewModel.showToast.observe(this, Observer {
            Toast.makeText(activity, "Выберите хотябы один контакт", Toast.LENGTH_SHORT)
        })

        viewModel.navigateComplete.observe(this, Observer {
/*            var intent = Intent()
            intent.putExtra("selectedContacts", viewModel.selectedContacts)
            setResult(DaggerAppCompatActivity.RESULT_OK, intent)
            finish()*/
            TODO("return result to previous fragment")
        })

        return view
    }

    override fun onItemClick(position: Int) {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ContactViewModel::class.java)

        viewModel.selectItemByPosition(position)
    }

}
