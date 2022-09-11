package website.italojar.klikincommerces.domain.usecase

import website.italojar.klikincommerces.data.repository.CommercesRepositoryImpl
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CommercesRepositoryImpl
) {
    suspend operator fun invoke(): List<String>{
        val commerces = repository.getAllCommerces().filter { it.active }
        return if(commerces.isNotEmpty()){
            val allCommerceCategories = commerces.map { commerceVO ->
                commerceVO.category ?: "OTHER"
            }
            val categoriesGroup = allCommerceCategories.groupBy { category -> category }
            categoriesGroup.mapNotNull { name -> name.key }
        } else { emptyList() }
    }
}