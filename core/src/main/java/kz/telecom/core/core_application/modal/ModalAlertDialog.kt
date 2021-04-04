package kz.telecom.core.core_application.modal

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kz.telecom.core.core_application.R
import kz.telecom.core.core_application.databinding.ContentAlertDialogBinding
import kz.telecom.core.core_application.utils.extensions.gone

/*
    На Время захордкожен потом сделаю лучше
    Пока дизайна нет, так что дефолт будем использовать
    CoreAlertDialog
*/

class ModalAlertDialog(
    fm: FragmentManager,
    private val title: String,
    private val subTitle: String? = null,
    private val mainActionText: String,
    private val mainAction: (() -> Unit),
    private val secondaryActionText: String? = null,
    private val secondaryAction: (() -> Unit)? = null
) : DialogFragment() {
    init {
        show(fm, "")
    }

    private lateinit var binding: ContentAlertDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ContentAlertDialogBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@ModalAlertDialog.viewLifecycleOwner
        }
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        with(binding) {
            title.text = this@ModalAlertDialog.title
            this@ModalAlertDialog.subTitle?.let { subTitle.text = it } ?: subTitle.gone()

            btnMain.text = mainActionText
            btnMain.setOnClickListener {
                dialog?.cancel()
                mainAction()
            }

            secondaryActionText?.let { btnSecondary.text = it } ?: btnSecondary.gone()

            btnSecondary.setOnClickListener {
                dialog?.cancel()
                secondaryAction?.let { it() }
            }

        }
    }
}