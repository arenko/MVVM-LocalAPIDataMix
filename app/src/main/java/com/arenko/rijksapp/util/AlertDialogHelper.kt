package com.arenko.rijksapp.util

import android.content.Context
import androidx.appcompat.app.AlertDialog

object AlertDialogHelper {

    @JvmStatic
    fun showAlert(context: Context,
                  title: String?,
                  message: String?,
                  cancelable: Boolean) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(cancelable)
        builder.show()
    }

}