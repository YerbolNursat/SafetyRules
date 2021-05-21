package kz.dungeonmasters.profile.presentation.ui.achievements

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kz.dungeonmasters.core.core_application.presentation.content.CoreSimpleToolbar
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitSimpleToolbar
import kz.dungeonmasters.profile.BR
import kz.dungeonmasters.profile.R
import kz.dungeonmasters.profile.databinding.FragmentAchievementsBinding
import kz.dungeonmasters.profile.presentation.ui.models.AchievementsUi
import org.koin.androidx.viewmodel.ext.android.viewModel

class AchievementsFragment : CoreFragment<FragmentAchievementsBinding, AchievementsViewModel>() {

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override val viewModel: AchievementsViewModel by viewModel()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_achievements

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAchievementInfo()
        binding.achievementsRv.adapter = groupAdapter
        initToolbar()
    }
    private fun initToolbar() {
        standardInitSimpleToolbar(
            CoreSimpleToolbar("Достижения"),
            binding.toolbar.root
        )
    }

    override fun onStart() {
        super.onStart()
        viewModel.items.observe(viewLifecycleOwner, Observer(::handleItems))
    }

    private fun handleItems(data: List<AchievementsUi>) {
        groupAdapter.updateAsync(data)
    }

}