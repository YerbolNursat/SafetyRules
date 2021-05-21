package kz.dungeonmasters.tests.presentation.ui.notification

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kz.dungeonmasters.tests.databinding.DialogNotificationBinding

class NotificationDialog(
    fm: FragmentManager,
    private val title: String,
    private val subTitle: String? = null,
    private val mainActionText: String,
) : DialogFragment() {
    init {
        show(fm, "")
    }

    private lateinit var binding: DialogNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogNotificationBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@NotificationDialog.viewLifecycleOwner
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
            titleTv.text = title
            subtitleTv.text = subTitle

            btnMain.text = mainActionText
            btnMain.setOnClickListener {
                dialog?.cancel()
            }

        }
    }
}