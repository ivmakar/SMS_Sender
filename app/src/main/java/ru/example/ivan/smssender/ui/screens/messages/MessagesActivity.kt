package ru.example.ivan.smssender.ui.screens.messages

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ru.example.ivan.smssender.R

class MessagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        title = this.intent.getStringExtra("chainName")
        this.setTitle(title.toString())
    }
}
