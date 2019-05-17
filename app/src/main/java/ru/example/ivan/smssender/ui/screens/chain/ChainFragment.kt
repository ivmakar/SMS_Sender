package ru.example.ivan.smssender.ui.screens.chain


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.findNavController
import dagger.android.support.DaggerFragment
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.FragmentChainBinding
import ru.example.ivan.smssender.ui.rvadapters.ChainRecyclerViewAdapter
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

        binding.chainRv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        binding.chainRv.adapter = chainRecyclerViewAdapter
        viewModel.chains.observe(this,
            Observer<ArrayList<Chain>> { it?.let{ chainRecyclerViewAdapter.replaceData(it)} })

        viewModel.navigateToGroups.observe(this, Observer {
            findNavController(this).navigate(R.id.action_chainFragment_to_groupFragment)
        })

        return view
    }

    override fun onItemClick(position: Int) {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ChainViewModel::class.java)

        var bundle = Bundle()
        bundle.putString("chainName", viewModel.getChainByPosition(position).chainName)
        bundle.putInt("groupId", viewModel.getChainByPosition(position).groupId!!)
        findNavController(this).navigate(R.id.action_chainFragment_to_messagesFragment, bundle)
    }

}
