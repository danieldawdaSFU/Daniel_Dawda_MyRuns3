package com.example.daniel_dawda_myruns3

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import java.io.File

// adapted from dialog demo
class ProfileDialogs : DialogFragment(), DialogInterface.OnClickListener {

    interface PhotoPickerCallback {
        fun pickPhotoFromCamera()
        fun pickPhotoFromGallery()
    }
    private var callback: PhotoPickerCallback? = null
    val tempImgFileName = "myruns_photo.jpg"

    companion object {
        const val DIALOG_KEY = "profile dialog"
        const val SAVE_FAILED_DIALOG = 1
        const val CAMERA_DIALOG = 2
    }

    // send image back to parent Activity generated via GPT
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = if (context is PhotoPickerCallback) context else null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        lateinit var ret: Dialog
        val bundle = arguments
        val dialogId = bundle?.getInt(DIALOG_KEY)
        if (dialogId == SAVE_FAILED_DIALOG) {
            val builder = AlertDialog.Builder(requireActivity())
            val view: View = requireActivity().layoutInflater.inflate(
                R.layout.photo_fragment,
                null
            )

            builder.setView(view)
            builder.setTitle("Save Failed")
            builder.setMessage("Please ensure conditions are met:\n - Photo added\n - name, email, major under 64 characters\n - Phone must be 10 character\n - Gender Selected\n - Phone, Grad: Numeric")

            builder.setPositiveButton("Ok", this)
            ret = builder.create()
        }
        if (dialogId == CAMERA_DIALOG) {
            val builder = AlertDialog.Builder(requireActivity())
            val view: View = requireActivity().layoutInflater.inflate(
                R.layout.photo_fragment,
                null
            )

            builder.setView(view)
            builder.setTitle("Pick Photo Option")
            builder.setPositiveButton("Camera", this)
            builder.setNegativeButton("Gallery", this)

            ret = builder.create()
        }
        return ret
    }

    override fun onClick(dialog: DialogInterface, item: Int) {

        val dialogId = arguments?.getInt(DIALOG_KEY)
        when (dialogId) {
            SAVE_FAILED_DIALOG -> {
                if (item == DialogInterface.BUTTON_POSITIVE) {
                    Toast.makeText(activity, "Ok", Toast.LENGTH_LONG).show()
                } else if (item == DialogInterface.BUTTON_NEGATIVE) {
                    Toast.makeText(activity, "Cancel", Toast.LENGTH_LONG).show()
                }
            }

            CAMERA_DIALOG -> {
                if (item == DialogInterface.BUTTON_POSITIVE) {
                    callback?.pickPhotoFromCamera()
                } else if (item == DialogInterface.BUTTON_NEGATIVE) {
                    callback?.pickPhotoFromGallery()
                }
            }
        }
    }


    private fun createTempUri(): Uri {
        val tempFile = File(requireContext().getExternalFilesDir(null), tempImgFileName)
        return FileProvider.getUriForFile(requireContext(), "com.daniel.myruns2camera", tempFile)
    }
}