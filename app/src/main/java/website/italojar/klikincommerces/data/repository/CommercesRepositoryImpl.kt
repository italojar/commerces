package website.italojar.klikincommerces.data.repository

import website.italojar.klikincommerces.data.model.dto.CommerceDto
import website.italojar.klikincommerces.data.source.network.service.CommercesService
import javax.inject.Inject

class CommercesRepositoryImpl @Inject constructor(
    private val service: CommercesService
) : ICommercesRepository {

    override suspend fun getAllCommerces(): List<CommerceDto> {
        return service.getCommercesList()
    }
}