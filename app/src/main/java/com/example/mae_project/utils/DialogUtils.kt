package com.example.mae_project.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mae_project.databinding.ProgresslayoutBinding


class DialogUtils(context: Context) : Dialog(context) {
    var dismiss: (() -> Any?)? = null
     private val progressLayout: ProgresslayoutBinding by lazy {
         ProgresslayoutBinding.inflate(
             LayoutInflater.from(context)
         )
     }

    fun customDialog(view: View, isCancelable: Boolean) {
        setContentView(view)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setCancelable(isCancelable)
        show()
    }

      fun showProgressDialog(message: String, isCancelable: Boolean) {
          progressLayout.tvMessage.text = message
          setContentView(progressLayout.root)
          window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
          window?.setBackgroundDrawableResource(android.R.color.transparent)
          setCancelable(isCancelable)
          show()
      }


    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {
        super.setOnDismissListener(listener)
        dismiss?.invoke()
    }

    fun dismissDialog() {
        try {
            if (isShowing)
                dismiss()
        } catch (ex: java.lang.IllegalArgumentException) {
        }
    }
}