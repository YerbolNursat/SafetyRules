package kz.dungeonmasters.tests.presentation.ui.tests

import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.tests.BR
import kz.dungeonmasters.tests.R
import kz.dungeonmasters.tests.databinding.FragmentTestsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestsFragment :CoreFragment<FragmentTestsBinding,TestsViewModel>(){
    override val viewModel: TestsViewModel by viewModel()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int =R.layout.fragment_tests}