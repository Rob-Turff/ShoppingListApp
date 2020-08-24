package com.example.shoppinglistapp.ui.list.dialogboxes

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.shoppinglistapp.R

class DeleteListDialogFragment : DialogFragment() {
    private lateinit var listener: DeleteListDialogListener

    interface DeleteListDialogListener {
        fun onDeleteListDialogPositiveClick(dialog: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.delete_list_check)
                .setPositiveButton(
                    R.string.confirm
                ) { dialog, id ->
                    listener.onDeleteListDialogPositiveClick(this)
                }
                .setNegativeButton(
                    R.string.cancel
                ) { dialog, id ->
                    dialog.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            listener = targetFragment as DeleteListDialogListener
        } catch (e: java.lang.ClassCastException) {
            throw ClassCastException("Calling Fragment must implement NoticeDialogListener")
        }
    }
}