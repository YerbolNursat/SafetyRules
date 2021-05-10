package kz.dungeonmasters.messages.presentation.ui.messages

import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.messages.BR
import kz.dungeonmasters.messages.R
import kz.dungeonmasters.messages.databinding.FragmentMessagesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessagesFragment:CoreFragment<FragmentMessagesBinding,MessagesViewModel>() {
    override val viewModel: MessagesViewModel by viewModel()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int =R.layout.fragment_messages
}