package kz.dungeonmasters.home.domain.usecase

import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.domain.CoreUseCase
import kz.dungeonmasters.home.data.remote.HomeApi
import kz.dungeonmasters.home.data.repository.HomeRepository
import okhttp3.ResponseBody

class GetStaticFileUseCase(
    private val homeRepository: HomeRepository
) : CoreUseCase<String, ResponseBody> {

    override suspend fun execute(param: String): ResultApi<ResponseBody> = homeRepository.getStaticFiles(param)

}