package ru.example.ivan.smssender.ui.screens.main


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.android.support.DaggerAppCompatActivity
import ru.example.ivan.smssender.R
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }



}

