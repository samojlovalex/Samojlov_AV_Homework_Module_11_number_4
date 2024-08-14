package com.example.samojlov_av_homework_module_11_number_4


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class MyDialog : DialogFragment() {
    private var removable: Removable? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        removable = context as Removable?
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val note = requireArguments().getString ("note")
        val builder = AlertDialog.Builder(requireActivity())
        return builder
            .setTitle(getString(R.string.myDialogTitle))
            .setIcon(R.drawable.delete_user)
            .setMessage(getString(R.string.myDialogMessage, note))
            .setPositiveButton("Да") { _, _ ->
                if (note != null) {
                    removable?.remove(note)
                }
            }
            .setNeutralButton("Нет", null)
            .create()
    }
}