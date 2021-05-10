package kz.dungeonmasters.search.presentation.ui.search

import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.search.BR
import kz.dungeonmasters.search.R
import kz.dungeonmasters.search.databinding.FragmentSearchBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment:CoreFragment<FragmentSearchBinding,SearchViewModel>() {
    override val viewModel: SearchViewModel by viewModel()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_search
}