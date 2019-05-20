package ru.example.ivan.smssender.utility

class Constants {
    companion object {
        //Bundle keys
        const val KEY_TEMPLATE = "template"
        const val KEY_GROUP_ID = "group_id"
        const val KEY_GROUP_NAME = "group_name"
        const val KEY_GROUP_MEMBERS = "group_members"

        //Broadcast keys
        const val SELECTED_TEMPLATE = "selected-template"

        //Permissions
        const val REQUEST_CODE_PERMISSION_READ_CONTACTS = 1
        const val REQUEST_CODE_PERMISSION_READ_PHONE_STATE = 2
        const val REQUEST_CODE_PERMISSION_SEND_SMS = 3

        //Send date
        const val DATE_FORMAT = "EE, d MMMM, HH:mm"

        //Message status
        const val STATUS_SEND = "send"

        //SIM
        const val NO_SIM = "no_sim"

        //Send status
        const val STATUS_OK = 0
        const val STATUS_ERROR_NO_PARAMETERS = 1
    }
}