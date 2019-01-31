package ru.example.ivan.smssender.ui.screens.new_group

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.data.ContactRepository
import ru.example.ivan.smssender.databinding.ActivityNewGroupBinding
import ru.example.ivan.smssender.ui.rvadapters.NewGroupRecyclerViewAdapter
import ru.example.ivan.smssender.ui.screens.contacts.ContactActivity
import ru.example.ivan.smssender.ui.uimodels.Contact
import javax.inject.Inject

class NewGroupActivity : DaggerAppCompatActivity(), NewGroupRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: ActivityNewGroupBinding
    private val newGroupRecyclerViewAdapter = NewGroupRecyclerViewAdapter(arrayListOf(), this)
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val REQUEST_CODE: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_group)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_group)
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewGroupViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.groupRv.layoutManager = LinearLayoutManager(this)
        binding.groupRv.adapter = newGroupRecyclerViewAdapter
/*        viewModel.contacts.observe(this,
            Observer<ArrayList<Contact>> { it?.let { newGroupRecyclerViewAdapter.replaceData(it) } })*/

        viewModel.notifyAddContact.observe(this,
            Observer {
                newGroupRecyclerViewAdapter.replaceData(viewModel.contacts)
                if (viewModel.multipleChanging)
                    newGroupRecyclerViewAdapter.notifyItemRangeInserted(viewModel.notifyPositionStart, viewModel.notifyPositionEnd)
                else
                    newGroupRecyclerViewAdapter.notifyItemInserted(viewModel.notifyPositionStart)
//                newGroupRecyclerViewAdapter.notifyDataSetChanged()
            })

        viewModel.notifyRemoveContact.observe(this,
            Observer {
                newGroupRecyclerViewAdapter.replaceData(viewModel.contacts)
                newGroupRecyclerViewAdapter.notifyItemRemoved(viewModel.notifyPositionStart)
            })

        viewModel.navigateComplete.observe(this, Observer {
            //TODO: close Activity
        })

        viewModel.navigateAddContacts.observe(this, Observer {

            //TODO: start ContactsActivity for getting result
            intent = Intent(this, ContactActivity::class.java)

            startActivityForResult(intent, REQUEST_CODE)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                val viewModel = ViewModelProviders.of(this, viewModelFactory)
                    .get(NewGroupViewModel::class.java)

                var selectedContacts = data!!.extras.getSerializable("selectedContacts") as ArrayList<Contact>
                viewModel.addContacts(selectedContacts)
            }
        }
    }

    override fun onItemClick(position: Int) {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewGroupViewModel::class.java)

        viewModel.deleteItemByPosition(position)
    }
}
