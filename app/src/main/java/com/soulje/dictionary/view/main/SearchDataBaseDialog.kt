package com.soulje.dictionary.view.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.soulje.dictionary.R

class SearchDataBaseDialog : DialogFragment(){

    lateinit var editText: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val contentView: View =
            requireActivity().layoutInflater.inflate(R.layout.dialog_search, null)
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
            .setTitle("Введите задачу")
            .setView(contentView)
            .setPositiveButton(
                "Добавить",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    editText = contentView.findViewById(R.id.input_word)
                    val answer: String = editText.text.toString()
                    (requireActivity() as MainActivity).onDialogResult(answer)

                }
            )
        return builder.create()
    }
}