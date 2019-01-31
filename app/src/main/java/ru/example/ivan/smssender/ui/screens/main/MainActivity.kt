package ru.example.ivan.smssender.ui.screens.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.ActivityMainBinding
import ru.example.ivan.smssender.ui.rvadapters.ChainRecyclerViewAdapter
import ru.example.ivan.smssender.ui.screens.group.GroupActivity
import ru.example.ivan.smssender.ui.screens.messages.MessagesActivity
import ru.example.ivan.smssender.ui.uimodels.Chain
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), ChainRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val chainRecyclerViewAdapter = ChainRecyclerViewAdapter(arrayListOf(), this)
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ChainViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.chainRv.layoutManager = LinearLayoutManager(this)
        binding.chainRv.adapter = chainRecyclerViewAdapter
        viewModel.chains.observe(this,
            Observer<ArrayList<Chain>> { it?.let{ chainRecyclerViewAdapter.replaceData(it)} })

        viewModel.navigateToGroups.observe(this, Observer {
            startActivity(Intent(this, GroupActivity::class.java))
        })

    }

    override fun onItemClick(position: Int) {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ChainViewModel::class.java)
//        viewModel.chainOnClick(position)*/

        intent = Intent(this, MessagesActivity::class.java)
        intent.putExtra("chainName", viewModel.getChainNameByPosition(position))
        startActivity(intent)
    }

}

