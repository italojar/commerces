package website.italojar.klikincommerces.presentation.components.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import website.italojar.klikincommerces.R
import website.italojar.klikincommerces.presentation.interfaces.IDialogListener

class DistanceDialog : DialogFragment() {
    private lateinit var listener: IDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {// Where
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Selecciona una distancia")
                .setItems(R.array.distances,
                    DialogInterface.OnClickListener { dialog, which ->
                        when(which) {
                            0 -> {listener.onDialogPositiveClick(1000)}
                            1 -> {listener.onDialogPositiveClick(3000)}
                            2 -> {listener.onDialogPositiveClick(5000)}
                            3 -> {listener.onDialogPositiveClick(10000)}
                            4 -> {listener.onDialogPositiveClick(20000)}
                            5 -> {listener.onDialogPositiveClick(50000)}
                            else -> {listener.onDialogPositiveClick(1000)}
                        }
                    })

            val inflater = requireActivity().layoutInflater;
            builder.setView(inflater.inflate(R.layout.item_select_distance, null))
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as IDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement IDialogListener"))
        }
    }

}