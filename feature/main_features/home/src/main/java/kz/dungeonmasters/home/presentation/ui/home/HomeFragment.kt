package kz.dungeonmasters.home.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kz.dungeonmasters.core.core_application.presentation.content.CoreSimpleToolbar
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitSimpleToolbar
import kz.dungeonmasters.home.BR
import kz.dungeonmasters.home.R
import kz.dungeonmasters.home.databinding.FragmentHomeBinding
import kz.dungeonmasters.home.presentation.ui.models.CategoryCardUi
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : CoreFragment<FragmentHomeBinding, HomeViewModel>() {

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override val viewModel: HomeViewModel by viewModel()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getItems()
        initViews()
        initToolbar()
    }

    private fun initToolbar() {
        standardInitSimpleToolbar(
            CoreSimpleToolbar("Теория", needShowBack = false),
            binding.toolbar.root
        )
    }

    private fun initViews() {
        binding.homeRv.adapter = groupAdapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.items.observe(viewLifecycleOwner, Observer(::handleItems))
    }

    private fun handleItems(data: List<CategoryCardUi>) {
        binding.homeRv.layoutManager = GridLayoutManager(context, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return data[position].size
                }
            }
        }
        groupAdapter.updateAsync(data)
        groupAdapter.setOnItemClickListener { item, view ->
            when (item) {
                is CategoryCardUi -> {
                    findNavController().navigate(
                        R.id.action_homeFragment_to_theoryFragment,
                        bundleOf("CategoryCode" to item.code)
                    )
                }
            }
        }
    }

}
