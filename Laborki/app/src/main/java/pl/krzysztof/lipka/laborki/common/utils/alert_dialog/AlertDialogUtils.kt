package pl.krzysztof.lipka.laborki.common.utils.alert_dialog

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import javax.inject.Inject

interface AlertDialogUtils {

    fun showAlertDialog(
        context: Context,
        alertDialogParams: AlertDialogParams,
        positiveButtonClickListener: DialogInterface.OnClickListener,
        negativeButtonClickListener: DialogInterface.OnClickListener? = null
    )
}

class AlertDialogUtilsImpl @Inject constructor() : AlertDialogUtils {

    override fun showAlertDialog(
        context: Context,
        alertDialogParams: AlertDialogParams,
        positiveButtonClickListener: DialogInterface.OnClickListener,
        negativeButtonClickListener: DialogInterface.OnClickListener?
    ) {
        AlertDialog.Builder(context)
            .setIcon(alertDialogParams.iconResId)
            .setTitle(alertDialogParams.titleResId)
            .setMessage(alertDialogParams.messageResId)
            .setPositiveButton(alertDialogParams.positiveButtonTextResId, positiveButtonClickListener)
            .setNegativeButton(alertDialogParams.negativeButtonTextResId, negativeButtonClickListener)
            .show()
    }
}
