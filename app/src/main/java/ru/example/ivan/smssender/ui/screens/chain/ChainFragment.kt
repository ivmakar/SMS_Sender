package ru.example.ivan.smssender.ui.screens.chain


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.FragmentChainBinding
import ru.example.ivan.smssender.ui.rvadapters.ChainRecyclerViewAdapter
import ru.example.ivan.smssender.ui.screens.group.GroupActivity
import ru.example.ivan.smssender.ui.screens.messages.MessagesActivity
import ru.example.ivan.smssender.ui.uimodels.Chain
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ChainFragment : DaggerFragment(), ChainRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: FragmentChainBinding
    private val chainRecyclerViewAdapter = ChainRecyclerViewAdapter(arrayListOf(), this)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chain, container, false)
        var view = binding.root

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ChainViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.chainRv.layoutManager = LinearLayoutManager(activity)
        binding.chainRv.adapter = chainRecyclerViewAdapter
        viewModel.chains.observe(this,
            Observer<ArrayList<Chain>> { it?.let{ chainRecyclerViewAdapter.replaceData(it)} })

        viewModel.navigateToGroups.observe(this, Observer {
            startActivity(Intent(activity, GroupActivity::class.java))
        })

        return view
    }

    override fun onItemClick(position: Int) {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ChainViewModel::class.java)
//        viewModel.chainOnClick(position)*/

        var intent = Intent(activity, MessagesActivity::class.java)
        intent.putExtra("chainName", viewModel.getChainNameByPosition(position))
        startActivity(intent)
    }

}