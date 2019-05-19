package ru.example.ivan.smssender.ui.screens.new_message


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import com.google.android.material.textfield.TextInputEditText
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import dagger.android.support.DaggerFragment
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.FragmentNewMessageBinding
import ru.example.ivan.smssender.utility.Constants
import java.util.*

import javax.inject.Inject



/**
 * A simple [Fragment] subclass.
 *
 */
class NewMessageFragment : DaggerFragment() {

    private var broadcastReceiver: BroadcastReceiver? = null
    private var draftMessageText = String()
    private var isDraftMessageTextChanged = false

    private lateinit var binding: FragmentNewMessageBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_message, container, false)
        var view = binding.root

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewMessageViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        activity!!.title = "Новое сообщение"

        arguments?.getLong(Constants.KEY_GROUP_ID)?.let { viewModel.loadGroup(it) }

        viewModel.navigateComplete.observe(this, Observer {
            NavHostFragment.findNavController(this).navigate(R.id.action_newMessageFragment_to_chainFragment)
        })

        var selectTemplateButton = view.findViewById<Button>(R.id.select_template_button)
        selectTemplateButton.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_newMessageFragment_to_templateFragment)
        }

        var timeEditText = view.findViewById<TextInputEditText>(R.id.schedule_time_edit_text)
        timeEditText.setOnClickListener {
            showDatePickerDialog()
        }

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(contxt: Context?, intent: Intent?) {
                when (intent?.action) {
                    Constants.SELECTED_TEMPLATE -> {
                        val bundle = intent.extras
                        if (bundle != null) {
                            draftMessageText = bundle.getString(Constants.KEY_TEMPLATE, "")
                            isDraftMessageTextChanged = true
                        }
                    }
                }
            }
        }

        val filter = IntentFilter(Constants.SELECTED_TEMPLATE)
        androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(context!!)
            .registerReceiver(broadcastReceiver!!, filter)


        return view
    }

    override fun onResume() {
        super.onResume()

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewMessageViewModel::class.java)

        if (isDraftMessageTextChanged) {
            viewModel.messageText.set(draftMessageText)
            isDraftMessageTextChanged = false
        }

    }

    private fun showDatePickerDialog() {
        val curCalendar: Calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            context,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->

                    val viewModel = ViewModelProviders.of(this, viewModelFactory)
                        .get(NewMessageViewModel::class.java)
                    viewModel.scheduleDate.set(year, month, dayOfMonth, 0, 0, 0)

                    showTimePickerDialog()
                },
                curCalendar.get(Calendar.YEAR),
                curCalendar.get(Calendar.MONTH),
                curCalendar.get(Calendar.DAY_OF_MONTH)
            )
        datePicker.show()
    }

    private fun showTimePickerDialog() {
        val curCalendar: Calendar = Calendar.getInstance()
        val timePicker = TimePickerDialog(
            context,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val viewModel = ViewModelProviders.of(this, viewModelFactory)
                    .get(NewMessageViewModel::class.java)
                viewModel.scheduleDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
                viewModel.scheduleDate.set(Calendar.MINUTE, minute)
                viewModel.writeDate()
            },
            curCalendar.get(Calendar.HOUR_OF_DAY),
            curCalendar.get(Calendar.MINUTE),
            true
        )
        timePicker.show()

    }

    override fun onDetach() {
        super.onDetach()
        androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(this.context!!).unregisterReceiver(broadcastReceiver!!)
    }
}
