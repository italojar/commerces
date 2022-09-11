package website.italojar.klikincommerces.domain.irepository

import website.italojar.klikincommerces.domain.model.Commerce

interface ICommercesRepository {
    suspend fun getAllCommerces(): List<Commerce>
}