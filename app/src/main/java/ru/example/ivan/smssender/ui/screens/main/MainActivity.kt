package ru.example.ivan.smssender.ui.screens.main

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import ru.example.ivan.smssender.App
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.ActivityMainBinding
import ru.example.ivan.smssender.ui.rvadapters.ChainRecyclerViewAdapter
import ru.example.ivan.smssender.ui.uimodels.Chain
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ChainRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val chainRecyclerViewAdapter = ChainRecyclerViewAdapter(arrayListOf(), this)
    @Inject lateinit var viewModel: ChainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(activity = this@MainActivity)

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.chainRv.layoutManager = LinearLayoutManager(this)
        binding.chainRv.adapter = chainRecyclerViewAdapter
        viewModel.chains.observe(this,
            Observer<ArrayList<Chain>> { it?.let{ chainRecyclerViewAdapter.replaceData(it)} })

    }

    override fun onItemClick(position: Int) {
        //
    }
}

