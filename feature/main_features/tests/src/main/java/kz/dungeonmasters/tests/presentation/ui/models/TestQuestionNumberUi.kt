package kz.dungeonmasters.tests.presentation.ui.models

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ViewDataBinding
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.tests.BR
import kz.dungeonmasters.tests.R
import kz.dungeonmasters.tests.databinding.ItemTestQuestionNumberBinding

data class TestQuestionNumberUi(
    val text: String,
    val numberColor:Int = R.color.secondary
) : BindableItem<ViewDataBinding>() {

    val value = Value()

    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemTestQuestionNumberBinding -> {
                viewBinding.item = this
                value.numberColor = numberColor
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_test_question_number
    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemTestQuestionNumberBinding.bind(view)

    class Value : BaseObservable() {
        @Bindable
        var numberColor: Int? = null
            set(value) {
                field = value
                notifyPropertyChanged(BR.numberColor)
            }

    }
}