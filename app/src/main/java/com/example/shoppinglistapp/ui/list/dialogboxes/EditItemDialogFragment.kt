package com.example.shoppinglistapp.ui.list.dialogboxes

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.database.models.Item

class EditItemDialogFragment : DialogFragment() {
    private lateinit var listener: EditItemDialogListener

    interface EditItemDialogListener {
        fun onEditDialogPositiveClick(dialog: DialogFragment, text: String, item: Item)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.enter_string_dialog, null)
            val editText = view.findViewById<EditText>(R.id.rename_list_dialog_input)
            val item = arguments?.get("item") as Item
            editText.setText(item.itemName)
            builder.setView(view)
                .setTitle("Edit Item")
                .setPositiveButton(R.string.rename_item) { dialog, id ->
                    listener.onEditDialogPositiveClick(
                        this, editText.text.toString(), item
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
            listener = targetFragment as EditItemDialogListener
        } catch (e: java.lang.ClassCastException) {
            throw ClassCastException("Calling Fragment must implement NoticeDialogListener")
        }
    }
}