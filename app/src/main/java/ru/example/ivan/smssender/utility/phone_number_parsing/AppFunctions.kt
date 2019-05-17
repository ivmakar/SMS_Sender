package ru.example.ivan.smssender.utility.phone_number_parsing

class AppFunctions {
    companion object {
        fun formatPhoneNumber(number: String): String {
            var resultNumber = String()

            resultNumber = standartizePhoneNumber(number)
            if (resultNumber.length == 12){
                return resultNumber.substring(0, 2) +
                        "(" +
                        resultNumber.substring(2, 5) +
                        ")" +
                        resultNumber.substring(5, 8) +
                        "-" +
                        resultNumber.substring(8, 10) +
                        "-" +
                        resultNumber.substring(10, 12)
            }

            return number
        }

        fun standartizePhoneNumber(number: String): String {
            var resultNumber = String()

            for (i in number) {
                if (i.isDigit() || i == '+') {
                    resultNumber += i
                }
            }

            if (resultNumber[0] == '8') {
                resultNumber = "+7" + resultNumber.substring(1)
            }

            return resultNumber
        }
    }
}


