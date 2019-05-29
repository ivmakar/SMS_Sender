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
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Button
import androidx.appcompat.widget.AppCompatSpinner
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerFragment
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.databinding.FragmentNewMessageBinding
import ru.example.ivan.smssender.ui.rvadapters.SimInfoSpinnerAdapter
import ru.example.ivan.smssender.utility.Constants
import java.util.*

import javax.inject.Inject
import ru.example.ivan.smssender.ui.rvadapters.SimInfoSpinnerAdapter as SimInfoSpinnerAdapter1


/**
 * A simple [Fragment] subclass.
 *
 */
class NewMessageFragment : DaggerFragment() {

    private var broadcastReceiver: BroadcastReceiver? = null
    private var draftMessageText = String()
    private var isDraftMessageTextChanged = false
    private var groupId: Long = -1
    private var isGroupSelected = false

    private lateinit var binding: FragmentNewMessageBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private fun setupUi() {
        val bottomNavigationView = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.visibility = View.GONE

        activity?.let { it.title = "Новое сообщение" }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_message, container, false)
        var view = binding.root

        setupUi()

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewMessageViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        if (!isGroupSelected) {
            arguments?.getLong(Constants.KEY_GROUP_ID)?.let { viewModel.loadGroup(it) }
        }

        viewModel.navigateComplete.observe(this, Observer {

            var bundle = Bundle()
            bundle.putLong(Constants.KEY_GROUP_ID, viewModel.group.id!!)
            NavHostFragment.findNavController(this).navigate(R.id.action_newMessageFragment_to_messagesFragment, bundle)
//            NavHostFragment.findNavController(this).popBackStack()
        })

        var selectTemplateButton = view.findViewById<Button>(R.id.select_template_button)
        selectTemplateButton.setOnClickListener {
            var bundle = Bundle()
            bundle.putBoolean(Constants.KEY_IS_SELECTION_FRAGMENT, true)
            NavHostFragment.findNavController(this).navigate(R.id.action_newMessageFragment_to_templateFragment, bundle)
        }

        var timeEditText = view.findViewById<TextInputEditText>(R.id.schedule_time_edit_text)
        timeEditText.setOnClickListener {
            showDatePickerDialog()
        }

        var groupEditText = view.findViewById<TextInputEditText>(R.id.group_edit_text)
        groupEditText.setOnClickListener {
            var bundle = Bundle()
            bundle.putBoolean(Constants.KEY_IS_SELECTION_FRAGMENT, true)
            NavHostFragment.findNavController(this).navigate(R.id.groupFragment, bundle)
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
                    Constants.SELECTED_GROUP -> {
                        val bundle = intent.extras
                        bundle?.getLong(Constants.KEY_GROUP_ID)?.let {
                            groupId = it
                            isGroupSelected = true
                        }
                    }
                }
            }
        }

        val filter = IntentFilter(Constants.SELECTED_TEMPLATE)
        filter.addAction(Constants.SELECTED_GROUP)
        androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(context!!)
            .registerReceiver(broadcastReceiver!!, filter)

        viewModel.simInfoList.observe(this, Observer {
            val simSpinner = view.findViewById<AppCompatSpinner>(R.id.select_sim_spinner)

            viewModel.simAdapter = SimInfoSpinnerAdapter(activity!!.applicationContext, viewModel.simInfoList.value!!)
            simSpinner.adapter = viewModel.simAdapter

            simSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.selectedSimPosition = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            };

            viewModel.selectedSimPosition = 0
        })


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

        if (isGroupSelected) {
            viewModel.loadGroup(groupId)
            isGroupSelected = false
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
        datePicker.datePicker.minDate = Date().time
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


    override fun onPause() {
        super.onPause()
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity!!.window.decorView.windowToken, 0)
    }


    override fun onDetach() {
        super.onDetach()
        androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(this.context!!).unregisterReceiver(broadcastReceiver!!)
    }
}
