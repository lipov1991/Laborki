package pl.krzysztof.lipka.laborki.common.utils.alert_dialog

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

fun Context.showAlertDialog(
    alertDialogParams: AlertDialogParams,
    positiveButtonClickListener: DialogInterface.OnClickListener,
    negativeButtonClickListener: DialogInterface.OnClickListener? = null
) {
    AlertDialog.Builder(this)
        .setIcon(alertDialogParams.iconResId)
        .setTitle(alertDialogParams.titleResId)
        .setMessage(alertDialogParams.messageResId)
        .setPositiveButton(alertDialogParams.positiveButtonTextResId, positiveButtonClickListener)
        .setNegativeButton(alertDialogParams.negativeButtonTextResId, negativeButtonClickListener)
        .show()
}
