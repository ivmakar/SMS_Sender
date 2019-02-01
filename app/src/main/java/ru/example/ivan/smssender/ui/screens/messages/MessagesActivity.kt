package ru.example.ivan.smssender.ui.screens.messages

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.ActivityMessagesBinding
import ru.example.ivan.smssender.ui.rvadapters.MessageRecyclerViewAdapter
import ru.example.ivan.smssender.ui.uimodels.Message
import javax.inject.Inject

class MessagesActivity : DaggerAppCompatActivity(), MessageRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMessagesBinding
    private val chainRecyclerViewAdapter = MessageRecyclerViewAdapter(arrayListOf(), this)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO: recieve chain data and display it

        binding = DataBindingUtil.setContentView(this, R.layout.activity_messages)
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MessagesViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.messageRv.layoutManager = LinearLayoutManager(this)
        binding.messageRv.adapter = chainRecyclerViewAdapter
        viewModel.messages.observe(this,
            Observer<ArrayList<Message>> { it?.let{ chainRecyclerViewAdapter.replaceData(it)} })

        viewModel.navigateToGroups.observe(this, Observer {
//            startActivity(Intent(this, GroupActivity::class.java))
        })

    }

    override fun onItemClick(position: Int) {
        //
    }
}