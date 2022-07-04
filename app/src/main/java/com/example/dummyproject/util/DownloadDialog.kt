package com.example.dummyproject.util

import android.app.Activity
import android.app.AlertDialog
import com.example.dummyproject.R

class DownloadDialog(private val activity: Activity) {
    private var dialog: AlertDialog? = null

    fun start() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater

        builder.setView(inflater.inflate(R.layout.custom_dialog, null))

        dialog = builder.create()
        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
    }
}