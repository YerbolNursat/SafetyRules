package kz.dungeonmasters.home.presentation.ui.home

import kz.dungeonmasters.home.BR
import kz.dungeonmasters.home.R
import kz.dungeonmasters.home.databinding.FragmentHomeBinding
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : CoreFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModel()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_home
}
