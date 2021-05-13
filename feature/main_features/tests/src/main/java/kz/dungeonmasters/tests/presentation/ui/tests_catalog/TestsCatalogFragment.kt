package kz.dungeonmasters.tests.presentation.ui.tests_catalog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kz.dungeonmasters.core.core_application.presentation.content.CoreSimpleToolbar
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitSimpleToolbar
import kz.dungeonmasters.tests.BR
import kz.dungeonmasters.tests.R
import kz.dungeonmasters.tests.databinding.FragmentTestsCatalogBinding
import kz.dungeonmasters.tests.presentation.ui.models.CategoryCardUi
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class TestsCatalogFragment :CoreFragment<FragmentTestsCatalogBinding,TestsCatalogViewModel>(){

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override val viewModel: TestsCatalogViewModel by viewModel()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int =R.layout.fragment_tests_catalog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getItems()
        initViews()
        initToolbar()
    }

    private fun initToolbar() {
        standardInitSimpleToolbar(CoreSimpleToolbar("Тесты",needShowBack = false), binding.toolbar.root)
    }

    private fun initViews() {
        binding.testsRv.adapter = groupAdapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.items.observe(viewLifecycleOwner, Observer(::handleItems))
    }

    private fun handleItems(data: List<CategoryCardUi>) {
        Timber.i("data are $data")
        groupAdapter.updateAsync(data)

        groupAdapter.setOnItemClickListener { item, view ->
            when (item) {
                is CategoryCardUi -> {
                    findNavController().navigate(
                        R.id.action_testsCatalogFragment_to_testsFragment,
                        bundleOf("CategoryCode" to item.id)
                    )
                }
            }
        }

    }

}