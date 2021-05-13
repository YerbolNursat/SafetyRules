package kz.dungeonmasters.tests.presentation.ui.test_detail

import android.os.Bundle
import android.view.View
import kz.dungeonmasters.core.core_application.presentation.content.CoreSimpleToolbar
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitSimpleToolbar
import kz.dungeonmasters.tests.BR
import kz.dungeonmasters.tests.R
import kz.dungeonmasters.tests.databinding.FragmentTestDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestDetailFragment : CoreFragment<FragmentTestDetailBinding, TestDetailViewModel>() {
    override val viewModel: TestDetailViewModel by viewModel()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_test_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
    }

    private fun initToolbar() {
        standardInitSimpleToolbar(CoreSimpleToolbar("Тесты"), binding.toolbar.root)
    }

}