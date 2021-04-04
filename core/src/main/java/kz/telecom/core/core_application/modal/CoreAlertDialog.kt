package kz.telecom.core.core_application.modal

import android.content.Context
import android.content.DialogInterface
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CoreAlertDialog(
    context: Context,
    private val title: String,
    private val subTitle: String? = null,
    private val mainActionText: String,
    private val mainAction: (() -> Unit),
    private val secondaryActionText: String? = null,
    private val secondaryAction: (() -> Unit)? = null
) {
    init {
        with(MaterialAlertDialogBuilder(context)) {
            setTitle(title)
            subTitle?.let { setMessage(it) }
            setPositiveButton(mainActionText) { dialog, _ ->
                dialog.dismiss()
                mainAction()
            }
            secondaryActionText?.let {
                setNegativeButton(secondaryActionText) { dialog, _ ->
                    dialog.dismiss()
                    secondaryAction?.let { it() }
                }
            }
            show()
        }
    }
}