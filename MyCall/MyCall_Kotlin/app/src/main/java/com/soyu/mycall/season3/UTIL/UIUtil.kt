package com.soyu.mycall.season3.UTIL

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.soyu.mycall.season3.R

class UIUtil {

    companion object {
        fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
        }


        //  20220616
        //  soyu Dialog view
        private var dialogView = false

        fun viewDialog(
            context: Activity, title: String?, message: String?,
            positiveStr: String?, positiveListener: DialogInterface.OnClickListener?,
            negativeStr: String?, negativeListener: DialogInterface.OnClickListener?,
            neutralStr: String?, neutralListener: DialogInterface.OnClickListener?
        ) {
            Log.e("!!!", "viewDialog : $dialogView")
            if (!dialogView) {
                dialogView = true
                val alt_bld = AlertDialog.Builder(context, R.style.MyDialogTheme)
                if (title != null) {
                    alt_bld.setTitle(title)
                }
                if (message != null) {
                    alt_bld.setMessage(message)
                }
                if (positiveStr != null) {
                    alt_bld.setPositiveButton(positiveStr, positiveListener)
                }
                if (negativeStr != null) {
                    alt_bld.setNegativeButton(negativeStr, negativeListener)
                }
                if (neutralStr != null) {
                    alt_bld.setNeutralButton(neutralStr, neutralListener)
                }
                val alert = alt_bld.create()
                alert.setCancelable(false)
                alert.setOnShowListener {
                    Log.e("!!!", "viewDialog : $dialogView")
                    dialogView = false
                }
                alert.show()
                val tv_dialog_message = alert.findViewById<View>(android.R.id.message) as TextView
                tv_dialog_message.setTypeface(context.resources.getFont(R.font.applesdgothicneor))
                tv_dialog_message.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13f)
            }
        }
    }

}