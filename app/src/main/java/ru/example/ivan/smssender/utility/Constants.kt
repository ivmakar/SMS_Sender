package ru.example.ivan.smssender.utility

class Constants {
    companion object {

        //Notifications
        const val NOTIFICATION_CHANNEL_ID = "sendSmsId"

        //showRequestPermissionRationale
        const val DIALOG_RATIONALE_TEXT = "Для корректной работы приложения необходимо предоставить разрешения.\n\n" +
                "Разрешение на осуществление телефонных звонков и управление ими необходимо для доступа к списку активных SIM-карт."
        const val DIALOG_BUTTON_OK = "OK"
        const val DIALOG_BUTTON_PROVIDE = "Предоставить"
        const val DIALOG_BUTTON_EXIT = "Выйти"

        //Bundle keys
        const val KEY_TEMPLATE = "template"
        const val KEY_GROUP_ID = "group_id"
        const val KEY_GROUP_NAME = "group_name"
        const val KEY_GROUP_MEMBERS = "group_members"
        const val KEY_MESSAGE_TO_USER_ID = "message_to_user_id"
        const val KEY_MESSAGE_ID = "message_id"
        const val KEY_MESSAGE_DETAIL_ID = "message_detail_id"
        const val KEY_IS_SELECTION_FRAGMENT = "is_selection_fragment"

        //Broadcast keys
        const val SELECTED_TEMPLATE = "selected-template"
        const val SELECTED_GROUP = "selected_group"

        //Permissions
        const val REQUEST_CODE_PERMISSION = 4
        const val REQUEST_CODE_PERMISSION_READ_CONTACTS = 1
        const val REQUEST_CODE_PERMISSION_READ_PHONE_STATE = 2
        const val REQUEST_CODE_PERMISSION_SEND_SMS = 3

        //Send date
        const val DATE_FORMAT = "EE, d MMMM, HH:mm"

        //Message status
        const val STATUS_SCHEDULE = "schedule"
        const val STATUS_SENDED = "sended"
        const val STATUS_SENT_OK = "sent"
        const val STATUS_DELIVERED = "delivered"
        const val STATUS_FAILURE_SEND = "failure"

        //SIM
        const val NO_SIM = "no_sim"

        //Send status
        const val STATUS_OK = 0
        const val STATUS_ERROR_NO_PARAMETERS = 1

        //Sent Delivered intent
        const val SMS_SENT_INTENT = "sms_sent"
        const val SMS_DELIVERED_INTENT = "sms_delivered"
    }
}