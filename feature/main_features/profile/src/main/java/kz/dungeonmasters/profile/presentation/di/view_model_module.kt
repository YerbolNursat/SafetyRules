package kz.dungeonmasters.profile.presentation.di

import kz.dungeonmasters.profile.presentation.ui.achievements.AchievementsViewModel
import kz.dungeonmasters.profile.presentation.ui.edit_profile.EditProfileViewModel
import kz.dungeonmasters.profile.presentation.ui.profile.ProfileViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainProfileViewModelModule = module {
    viewModel { ProfileViewModel(getProfileInfoUseCase = get()) }
    viewModel {
        EditProfileViewModel(
            getProfileInfoUseCase = get(),
            setProfileInfoUseCase = get(),
            getProfilePhotosUseCase = get()
        )
    }
    viewModel { AchievementsViewModel(getAchievementUseCase = get()) }
}