package com.example.shoppinglistapp.ui.list.dialogboxes

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.shoppinglistapp.R

class RenameListDialogFragment : DialogFragment() {
    private lateinit var listener: RenameListDialogListener

    interface RenameListDialogListener {
        fun onRenameListDialogPositiveClick(dialog: DialogFragment, text: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.enter_string_dialog, null)
            val editText = view.findViewById<EditText>(R.id.rename_list_dialog_input)
            editText.setText(activity?.title)
            builder.setView(view)
                .setTitle("Rename List")
                .setPositiveButton(R.string.rename_list) { dialog, id ->
                    listener.onRenameListDialogPositiveClick(
                        this,
                        editText.text.toString()
                    )
                }
                .setNegativeButton(
                    R.string.cancel
                ) { dialog, id -> dialog.cancel() }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            listener = targetFragment as RenameListDialogListener
        } catch (e: java.lang.ClassCastException) {
            throw ClassCastException("Calling Fragment must implement NoticeDialogListener")
        }
    }
}