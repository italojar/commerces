package website.italojar.klikincommerces.data.source.network.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import website.italojar.klikincommerces.data.model.dto.CommerceDto
import website.italojar.klikincommerces.data.model.dto.CommercesData
import website.italojar.klikincommerces.data.source.network.interfaces.ApiClient
import javax.inject.Inject

class CommercesService @Inject constructor(
    private val apiClient: ApiClient
) {
    suspend fun getCommercesList(): List<CommerceDto> {
        return withContext(Dispatchers.IO) {
            val response: Response<CommercesData> = apiClient.getAllCommerces()
            response.body() ?: emptyList()
        }
    }
}