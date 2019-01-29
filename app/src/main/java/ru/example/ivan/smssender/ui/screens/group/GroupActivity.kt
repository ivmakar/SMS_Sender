package ru.example.ivan.smssender.ui.screens.group

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import ru.example.ivan.smssender.App
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.ui.rvadapters.GroupRecyclerViewAdapter
import ru.example.ivan.smssender.ui.uimodels.Group
import ru.example.ivan.smssender.databinding.ActivityGroupBinding
import javax.inject.Inject

class GroupActivity : AppCompatActivity(), GroupRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: ActivityGroupBinding
    private val groupRecyclerViewAdapter = GroupRecyclerViewAdapter(arrayListOf(), this)
    @Inject lateinit var viewModel: GroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        App.appComponent.inject(activity = this@GroupActivity)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_group)

        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.groupRv.layoutManager = LinearLayoutManager(this)
        binding.groupRv.adapter = groupRecyclerViewAdapter
        viewModel.groups.observe(this,
            Observer<ArrayList<Group>> { it?.let{ groupRecyclerViewAdapter.replaceData(it)} })
    }

    override fun onItemClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
