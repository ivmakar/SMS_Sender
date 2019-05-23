package ru.example.ivan.smssender.ui.screens.messages


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerFragment
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.data.dbmodels.Group
import ru.example.ivan.smssender.databinding.FragmentMessagesBinding
import ru.example.ivan.smssender.ui.rvadapters.MessageDetailsRecyclerViewAdapter
import ru.example.ivan.smssender.data.dbmodels.Message
import ru.example.ivan.smssender.ui.rvadapters.MessageRecyclerViewAdapter
import ru.example.ivan.smssender.utility.Constants
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class MessagesFragment : DaggerFragment(), MessageRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: FragmentMessagesBinding
    private val messageRecyclerViewAdapter = MessageRecyclerViewAdapter(arrayListOf(), this)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_messages, container, false)
        var view = binding.root

        val bottomNavigationView = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.visibility = View.GONE

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MessagesViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.messageRv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        binding.messageRv.adapter = messageRecyclerViewAdapter

        arguments?.getLong(Constants.KEY_GROUP_ID)?.let { viewModel.loadGroup(it) }

        viewModel.curGroup.observe(this,
            Observer<Group> { it?.let{ activity!!.title = it.groupName } })

        arguments?.getLong(Constants.KEY_GROUP_ID)?.let { viewModel.loadMessages(it) }

        viewModel.messages.observe(this,
            Observer<ArrayList<Message>> { it?.let{
                messageRecyclerViewAdapter.replaceData(it)
                binding.messageRv.scrollToPosition(it.size - 1)
            } })

        viewModel.navigateToNewMessage.observe(this, Observer {
            var bundle = Bundle()
            bundle.putLong(Constants.KEY_GROUP_ID, viewModel.curGroup.value?.id!!)
            NavHostFragment.findNavController(this).navigate(R.id.action_messagesFragment_to_newMessageFragment, bundle)
        })

        return view
    }

    override fun onItemClick(position: Int) {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MessagesViewModel::class.java)
        var bundle = Bundle()
        bundle.putLong(Constants.KEY_MESSAGE_DETAIL_ID, viewModel.getMessageIdByPosition(position))
        NavHostFragment.findNavController(this).navigate(R.id.action_messagesFragment_to_messageDetailsFragment, bundle)

    }

}
