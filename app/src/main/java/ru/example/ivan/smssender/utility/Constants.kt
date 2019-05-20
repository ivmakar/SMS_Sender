package ru.example.ivan.smssender.utility

class Constants {
    companion object {
        //bundle keys
        const val KEY_TEMPLATE = "template"
        const val KEY_GROUP_ID = "group_id"
        const val KEY_GROUP_NAME = "group_name"
        const val KEY_GROUP_MEMBERS = "group_members"

        //broadcast keys
        const val SELECTED_TEMPLATE = "selected-template"

        //permissions
        const val REQUEST_CODE_PERMISSION_READ_CONTACTS = 1
        const val REQUEST_CODE_PERMISSION_READ_PHONE_STATE = 2

        //sendDate
        const val DATE_FORMAT = "EE, d MMMM, HH:mm"

        //MessageStatus
        const val STATUS_SEND = "send"

        //SIM
        const val SIM1 = "sim1"
        const val SIM2 = "sim2"
    }
}