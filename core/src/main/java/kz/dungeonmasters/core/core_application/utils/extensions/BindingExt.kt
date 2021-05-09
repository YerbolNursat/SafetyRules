package kz.dungeonmasters.core.core_application.utils.extensions

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.core.core_application.BR
import kz.dungeonmasters.core.core_application.R
import kz.dungeonmasters.core.core_application.presentation.content.CoreButton
import kz.dungeonmasters.core.core_application.presentation.content.CoreMoneyEditText
import kz.dungeonmasters.core.core_application.presentation.content.CoreSimpleToolbar
import kz.dungeonmasters.core.core_application.presentation.content.CoreSpinner
import kz.dungeonmasters.core.core_application.presentation.ui.widget.toolbar.MainToolbar


/**
 * Чтобы инициализовать баттон который был добавлен в xml через include
 */
fun standardInitButton(item: CoreButton, view: View) {
    val binding: ViewDataBinding? = DataBindingUtil.bind(view)
    binding?.setVariable(BR.data, item)
    item.iconDrawable?.let {
        with(binding?.root?.findViewById<ImageView>(R.id.button_icon)) {
            this?.setImageDrawable(ContextCompat.getDrawable(view.context, it))
        }
    }
}

fun standardInitMoneyEditText(item: CoreMoneyEditText, view: View): ViewDataBinding? {
    val binding: ViewDataBinding? = DataBindingUtil.bind(view)
    binding?.setVariable(BR.data, item)
    return binding
}

fun <T : Any> standardInitSpinner(item: CoreSpinner<T>, view: View, nameMapper: ((T) -> String)) {
    val binding: ViewDataBinding? = DataBindingUtil.bind(view)
    binding?.setVariable(BR.data, item)
    binding?.root?.findViewById<AppCompatSpinner>(R.id.spinner)?.apply {
        val names = item.items
            .map { nameMapper.invoke(it) }
            .toList()
        val adapter: ArrayAdapter<*> = ArrayAdapter(context, R.layout.content_spinner_item, names)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                item.actionOnSelect.invoke(item.items[p2])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
        setAdapter(adapter)
    }
}


/**
 * Устанавливает простой Тулбар в фрагмент
 */
fun Fragment.standardInitSimpleToolbar(item: CoreSimpleToolbar, view: View, action: (() -> Unit)? = null): ViewDataBinding? {
    val binding: ViewDataBinding? = DataBindingUtil.bind(view)
    binding?.setVariable(BR.simpleToolbar, item)
    binding?.root?.findViewById<ImageView>(R.id.ic_back)?.setOnClickListener {
        action?.let { action() } ?: activity?.onBackPressed()
    }
    return binding
}

fun Fragment.standardInitHomeToolbar(item: MainToolbar, view: View): ViewDataBinding? {
    val binding: ViewDataBinding? = DataBindingUtil.bind(view)
    binding?.setVariable(kz.dungeonmasters.core.core_application.BR.homeToolbar, item)
    binding?.root?.findViewById<ImageView>(kz.dungeonmasters.core.core_application.R.id.ic_back)?.setOnClickListener {
        activity?.onBackPressed()
    }
    return binding
}

/**
 * Устанавливает простой Тулбар в активити
 */
fun AppCompatActivity.standardInitSimpleToolbar(item: CoreSimpleToolbar, view: View) {
    val binding: ViewDataBinding? = DataBindingUtil.bind(view)
    binding?.setVariable(BR.simpleToolbar, item)
    binding?.root?.findViewById<ImageView>(R.id.ic_back)?.setOnClickListener {
        onBackPressed()
    }
}

@BindingAdapter("setBindingItems")
fun RecyclerView.setBindingItems(list: List<BindableItem<ViewDataBinding>>?) {
    list?.let {
        val adapter = GroupAdapter<GroupieViewHolder>()
        adapter.addAll(list)
        this.adapter = adapter
        this.adapter?.notifyDataSetChanged()
    }
}

@BindingAdapter(value = ["load"])
fun loadUrl(view: ImageView, url: Any?) {
    if (url == null) {
        return
    }
    view.loadImage(url)
}


@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}
