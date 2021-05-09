package kz.dungeonmasters.core.core_application.presentation.ui.dialogs

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kz.dungeonmasters.core.core_application.R
import kz.dungeonmasters.core.core_application.utils.extensions.gone
import kz.dungeonmasters.core.core_application.utils.extensions.visible


open class ModalBottomSheetDialog(
    val items: List<BaseBottomSheetDialogItem>,
    private val context1: Context,
    val transparent: Boolean = false,
    private val layoutId: Int? = null,
    private val dividers: List<RecyclerView.ItemDecoration>? = null,
    //if need not hide able
    private val isHideAble: Boolean = true,
    private val peekHide: Int? = null,
    private val background: Int? = null
) : BottomSheetDialog(context1) { // ОБЯЗАТЕЛЬНО нужно, чтоб они унаследовали BaseBottomSheetDialogItem, чтоб dismiss() использовать. его надо воткнуть в клик на айтем и/или кнопку закрытия

    init {
        show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutId?.let { setContentView(it) } ?: setContentView(R.layout.bottom_sheet_recycler)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        if (transparent) {
            val frame = findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            frame?.background = peekHide?.let {
                frame?.layoutParams?.height = (peekHide * 0.95).toInt()
                return@let ContextCompat.getDrawable(context1, background ?: R.drawable.bg_top_radius_r16)
            } ?: null
            findViewById<LinearLayout>(R.id.bottom_sheet)?.background = ContextCompat.getDrawable(
                context1, R.drawable.bg_top_radius_transparent
            )
        }

        if (!isHideAble && peekHide != null) {
            notHideAble()
        }
        findViewById<View>(R.id.rlCancel)?.setOnClickListener {
            dismiss()
        }
        initialise()
    }

    private fun initialise() {
        val groupAdapter = GroupAdapter<GroupieViewHolder>()
        items.forEach {
            it.actionForClose = { dismiss() }
        }
        groupAdapter.addAll(items)

        val rvBottomSheet = findViewById<RecyclerView>(R.id.rv_bottom_sheet)

        rvBottomSheet?.adapter = groupAdapter

        if (rvBottomSheet != null && dividers != null) {
            dividers.forEach {
                rvBottomSheet.addItemDecoration(it)
            }
        }

        behavior.state = peekHide?.let {
            behavior.peekHeight = it
            return@let BottomSheetBehavior.STATE_COLLAPSED
        } ?: BottomSheetBehavior.STATE_EXPANDED
    }

    fun showLoader() {
        val loader: View? = findViewById(R.id.loader)
        loader?.visible()
    }

    fun hideLoader() {
        val loader: View? = findViewById(R.id.loader)
        loader?.gone()
    }

    private fun notHideAble() {
        setCanceledOnTouchOutside(false)
        behavior.isHideable = isHideAble
        behavior.peekHeight = peekHide!!
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                }
            }
        })
    }

}