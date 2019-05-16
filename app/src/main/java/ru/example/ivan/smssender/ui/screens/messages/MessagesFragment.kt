package ru.example.ivan.smssender.ui.screens.messages


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import dagger.android.support.DaggerFragment
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.FragmentMessagesBinding
import ru.example.ivan.smssender.ui.rvadapters.MessageRecyclerViewAdapter
import ru.example.ivan.smssender.ui.uimodels.Message
import javax.inject.Inject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
        activity!!.title = arguments!!.getString("chainName")

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_messages, container, false)
        var view = binding.root

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MessagesViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.messageRv.layoutManager = LinearLayoutManager(activity)
        binding.messageRv.adapter = messageRecyclerViewAdapter
        viewModel.loadChains(arguments!!.getInt("groupId"))
        viewModel.messages.observe(this,
            Observer<ArrayList<Message>> { it?.let{ messageRecyclerViewAdapter.replaceData(it)} })

        viewModel.navigateToNewMessage.observe(this, Observer {
            NavHostFragment.findNavController(this).navigate(R.id.action_messagesFragment_to_newMessageFragment)
        })

        return view
    }

    override fun onItemClick(position: Int) {
        //
    }

}
