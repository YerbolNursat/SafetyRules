package kz.dungeonmasters.messages.presentation.ui.messages

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kz.dungeonmasters.messages.presentation.ui.forum.ForumFragment
import kz.dungeonmasters.messages.presentation.ui.notifications.NotificationsFragment

class MessagesAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    lateinit var fragment: Fragment
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        when (position) {
            0 -> {
                fragment = NotificationsFragment()
            }
            1 -> {
                fragment = ForumFragment()
            }
        }

        return fragment
    }
}