package pl.krzysztof.lipka.laborki.common.utils.alert_dialog

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class AlertDialogParams(
    @DrawableRes
    val iconResId: Int,
    @StringRes
    val titleResId: Int,
    @StringRes
    val messageResId: Int,
    @StringRes
    val positiveButtonTextResId: Int,
    @StringRes
    val negativeButtonTextResId: Int
)
