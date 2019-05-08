package ru.example.ivan.smssender

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var date: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("EE, d MMMM, HH:mm", Locale.getDefault())
        date.set(2020, 2, 15, 1, 20, 0)
        print(sdf.format(Date(date.timeInMillis)) + '\n')
        date.set(2020, 2, 15, 13, 20, 0)
        print(sdf.format(Date(date.timeInMillis)) + '\n')
    }
}
