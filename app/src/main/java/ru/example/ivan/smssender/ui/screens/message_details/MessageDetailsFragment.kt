package ru.example.ivan.smssender.ui.screens.message_details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerFragment
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.FragmentMessageDetailsBinding
import ru.example.ivan.smssender.ui.rvadapters.MessageDetailsRecyclerViewAdapter
import ru.example.ivan.smssender.ui.uimodels.MessageDetail
import ru.example.ivan.smssender.utility.Constants
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class MessageDetailsFragment : DaggerFragment(), MessageDetailsRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: FragmentMessageDetailsBinding
    private val messageDetailsRecyclerViewAdapter = MessageDetailsRecyclerViewAdapter(arrayListOf(), this)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private fun setupUi() {
        val bottomNavigationView = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.visibility = View.GONE

        activity?.let {
            it.title = "О сообщении"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message_details, container, false)
        var view = binding.root

        setupUi()

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MessageDetailsViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.messageRv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        binding.messageRv.adapter = messageDetailsRecyclerViewAdapter

        arguments?.getLong(Constants.KEY_MESSAGE_DETAIL_ID)?.let { viewModel.loadMessageDetailList(it) }

        viewModel.messageDetailList.observe(this,
            Observer<ArrayList<MessageDetail>> { it?.let{ messageDetailsRecyclerViewAdapter.replaceData(it) } })

        return view
    }

    override fun onItemClick(position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
