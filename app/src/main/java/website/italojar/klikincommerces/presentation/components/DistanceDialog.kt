package website.italojar.klikincommerces.presentation.components

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import website.italojar.klikincommerces.R

class DistanceDialog : DialogFragment() {
    private lateinit var listener: DialogListener

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface DialogListener {
        fun onDialogPositiveClick(distance: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {// Where
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Selecciona una distancia")
                .setItems(R.array.distances,
                    DialogInterface.OnClickListener { dialog, which ->
                        // The 'which' argument contains the index position
                        // of the selected item
                        when(which) {
                            0 -> {listener.onDialogPositiveClick(1000)}
                            1 -> {listener.onDialogPositiveClick(3000)}
                            2 -> {listener.onDialogPositiveClick(5000)}
                            3 -> {listener.onDialogPositiveClick(10000)}
                            4 -> {listener.onDialogPositiveClick(20000)}
                            else -> {listener.onDialogPositiveClick(1000)}
                        }
                    })

            val inflater = requireActivity().layoutInflater;
            builder.setView(inflater.inflate(R.layout.dialog_fragment_select_distance, null))
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the DialogListener so we can send events to the host
            listener = context as DialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement DialogListener"))
        }
    }

}