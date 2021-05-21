package kz.dungeonmasters.messages.presentation.ui.messages

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kz.dungeonmasters.core.core_application.presentation.content.CoreSimpleToolbar
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitSimpleToolbar
import kz.dungeonmasters.messages.BR
import kz.dungeonmasters.messages.R
import kz.dungeonmasters.messages.databinding.FragmentMessagesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MessagesFragment : CoreFragment<FragmentMessagesBinding, MessagesViewModel>() {

    override val viewModel: MessagesViewModel by viewModel()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_messages

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupAdapter()
    }

    private fun setupToolbar() {
        standardInitSimpleToolbar(
            CoreSimpleToolbar(
                text = "Уведомление",
                needShowBack= false,
                needShowMoreOption = true,
                moreOptionIcon = R.drawable.ic_filter
            ), binding.toolbar.root
        )
    }

    private fun setupAdapter() {
        Timber.i("setupAdapter")
        val adapter = MessagesAdapter(this)
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false
        val tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager, ::setupTabLayoutMediator)
        tabLayoutMediator.attach()
    }

    private fun setupTabLayoutMediator(tab: TabLayout.Tab, position: Int) {
        Timber.i("setupTabLayoutMediator, position = $position")
        tab.text = when (position) {
            0 -> "Уведомления"
            1 -> "Форум помощи"
            else -> null
        }
        binding.viewPager.setCurrentItem(tab.position, true)
    }

}