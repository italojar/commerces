package website.italojar.klikincommerces.domain.usecase

import website.italojar.klikincommerces.data.repository.CommercesRepositoryImpl
import website.italojar.klikincommerces.domain.model.Commerce
import javax.inject.Inject

class GetCommercesUseCase @Inject constructor(
    private val repository: CommercesRepositoryImpl
) {
    suspend operator fun invoke(): List<Commerce>{
        val commerces = repository.getAllCommerces()
        return if(commerces.isNotEmpty())
            commerces
        else
            emptyList()
    }
}