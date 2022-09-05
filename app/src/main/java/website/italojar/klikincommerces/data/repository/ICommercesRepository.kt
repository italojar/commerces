package website.italojar.klikincommerces.data.repository

import website.italojar.klikincommerces.data.model.dto.CommerceDto

interface ICommercesRepository {
    suspend fun getAllCommerces(): List<CommerceDto>
}