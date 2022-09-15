package website.italojar.klikincommerces.data.repository

import website.italojar.klikincommerces.data.mappers.toDomain
import website.italojar.klikincommerces.data.source.network.service.CommercesService
import website.italojar.klikincommerces.domain.irepository.ICommercesRepository
import website.italojar.klikincommerces.domain.model.Commerce
import javax.inject.Inject

class CommercesRepositoryImpl @Inject constructor(
    private val service: CommercesService
) : ICommercesRepository {

    override suspend fun getAllCommerces(): List<Commerce> {
        return service.getCommercesList().map { commerceDto -> commerceDto.toDomain()  }
    }
}