package ru.example.ivan.smssender

import org.junit.Test

import org.junit.Assert.*
import ru.example.ivan.smssender.utility.phone_number_parsing.AppFunctions
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

    @Test
    fun isCorrectPhoneParseFunctions() {
        assertEquals("+79298193340", AppFunctions.standartizePhoneNumber("8 (929) 819 33 40"))
        assertEquals("+79298193340", AppFunctions.standartizePhoneNumber("8 (929) 819-33-40"))
        assertEquals("+79298193340", AppFunctions.standartizePhoneNumber("8(929)819-33-40"))
        assertEquals("+79298193340", AppFunctions.standartizePhoneNumber("89298193340"))
        assertEquals("+79298193340", AppFunctions.standartizePhoneNumber("+7 (929) 819 33 40"))
        assertEquals("+79298193340", AppFunctions.standartizePhoneNumber("+7(929)819-33-40"))
        assertEquals("+79298193340", AppFunctions.standartizePhoneNumber("+79298193340"))

        assertEquals("+7(929)819-33-40", AppFunctions.formatPhoneNumber("8 (929) 819 33 40"))
        assertEquals("+7(929)819-33-40", AppFunctions.formatPhoneNumber("8 (929) 819-33-40"))
        assertEquals("+7(929)819-33-40", AppFunctions.formatPhoneNumber("8(929)819-33-40"))
        assertEquals("+7(929)819-33-40", AppFunctions.formatPhoneNumber("89298193340"))
        assertEquals("+7(929)819-33-40", AppFunctions.formatPhoneNumber("+7 (929) 819 33 40"))
        assertEquals("+7(929)819-33-40", AppFunctions.formatPhoneNumber("+7(929)819-33-40"))
        assertEquals("+7(929)819-33-40", AppFunctions.formatPhoneNumber("+79298193340"))
    }
}
