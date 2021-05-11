package kz.dungeonmasters.messages.presentation.ui.forum

import android.os.Bundle
import android.view.View
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.messages.BR
import kz.dungeonmasters.messages.R
import kz.dungeonmasters.messages.databinding.FragmentForumBinding
import kz.dungeonmasters.messages.presentation.ui.models.ForumUi
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForumFragment : CoreFragment<FragmentForumBinding, ForumViewModel>() {

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override val viewModel: ForumViewModel by viewModel()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_forum

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setData()
    }

    private fun initViews() {
        binding.forumRv.adapter = groupAdapter
    }

    private fun setData() {
        val data = listOf(
            ForumUi(),
            ForumUi(),
            ForumUi(),
            ForumUi(),
            ForumUi(),
            ForumUi(),
            ForumUi(),
            ForumUi()
        )
        groupAdapter.updateAsync(data)
    }

}