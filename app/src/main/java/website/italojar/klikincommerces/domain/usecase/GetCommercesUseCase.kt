package website.italojar.klikincommerces.domain.usecase

import website.italojar.klikincommerces.data.model.dto.CommerceDto
import website.italojar.klikincommerces.data.repository.CommercesRepositoryImpl
import javax.inject.Inject

class GetCommercesUseCase @Inject constructor(
    private val repository: CommercesRepositoryImpl
) {
    suspend operator fun invoke():List<CommerceDto>{
        val commerces = repository.getAllCommerces()

        return if(commerces.isNotEmpty()){
            commerces
        }else{
            emptyList()
        }
    }
}