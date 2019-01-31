package ru.example.ivan.smssender.ui.screens.contacts

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import dagger.android.support.DaggerAppCompatActivity
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.ActivityContactBinding
import ru.example.ivan.smssender.ui.rvadapters.ContactRecyclerViewAdapter
import ru.example.ivan.smssender.ui.uimodels.Contact
import javax.inject.Inject

class ContactActivity : DaggerAppCompatActivity(), ContactRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: ActivityContactBinding
    private val contactRecyclerViewAdapter = ContactRecyclerViewAdapter(arrayListOf(), this)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact)
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ContactViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.contactRv.layoutManager = LinearLayoutManager(this)
        binding.contactRv.adapter = contactRecyclerViewAdapter
        viewModel.contacts.observe(this,
            Observer<ArrayList<Contact>> { it?.let{ contactRecyclerViewAdapter.replaceData(it)} })

        viewModel.showToast.observe(this, Observer {
            Toast.makeText(this, "Выберите хотябы один контакт", Toast.LENGTH_SHORT)
        })

        viewModel.navigateComplete.observe(this, Observer {
            intent = Intent()
            intent.putExtra("selectedContacts", viewModel.selectedContacts)
            setResult(RESULT_OK, intent)
            finish()
        })

    }

    override fun onItemClick(position: Int) {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ContactViewModel::class.java)

        viewModel.selectItemByPosition(position)
    }
}
