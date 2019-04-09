package pl.krzysztof.lipka.laborki.common.utils.alert_dialog

import pl.krzysztof.lipka.laborki.R

enum class AlertDialogParamTypes(
    val value: AlertDialogParams
) {
    EXIT_APP_CONFIRMATION(
        AlertDialogParams(
            R.drawable.ic_info,
            R.string.app_name,
            R.string.exit_app_confirmation,
            R.string.yes,
            R.string.no
        )
    )
}
