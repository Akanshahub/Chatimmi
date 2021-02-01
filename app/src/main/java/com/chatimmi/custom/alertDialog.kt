package com.chatimmi.custom

import android.content.Context


open class alertDialog(context: Context,
                       var listener: DialogInterface,
                       var title: String,
                       var message: String,
                       var yesButtonText: String,
                       var noButtonText: String) {

    private fun alertDialog(context: Context) {
        val dialog = androidx.appcompat.app.AlertDialog.Builder(context)
        dialog.setCancelable(false)
        dialog.setTitle(title)
        dialog.setMessage(message)
        dialog.setPositiveButton(yesButtonText) { dialogInterface, i -> listener.btnOnclick("Yes") }
        dialog.setNegativeButton(noButtonText) { dialogInterface, i -> listener.btnOnclick("No") }
        dialog.show()
    }

    interface DialogInterface {
        fun btnOnclick(str: String?)
    }

    init {
        alertDialog(context)
    }
}