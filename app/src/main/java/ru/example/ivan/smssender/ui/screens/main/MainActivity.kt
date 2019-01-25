package ru.example.ivan.smssender.ui.screens.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.ActivityMainBinding
import ru.example.ivan.smssender.ui.rvadapters.ChainRecyclerViewAdapter
import ru.example.ivan.smssender.ui.uimodels.Chain

class MainActivity : AppCompatActivity(), ChainRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val chainRecyclerViewAdapter = ChainRecyclerViewAdapter(arrayListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this).get(ChainViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.chainRv.layoutManager = LinearLayoutManager(this)
        binding.chainRv.adapter = chainRecyclerViewAdapter
        viewModel.chains.observe(this,
            Observer<ArrayList<Chain>> { it?.let{ chainRecyclerViewAdapter.replaceData(it)} })

    }

    override fun onItemClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

