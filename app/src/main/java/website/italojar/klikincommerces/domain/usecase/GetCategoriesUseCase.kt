package website.italojar.klikincommerces.domain.usecase

import retrofit2.HttpException
import website.italojar.klikincommerces.data.repository.CommercesRepositoryImpl
import website.italojar.klikincommerces.domain.model.ResponseCategories
import website.italojar.klikincommerces.domain.model.ResponseCommerces
import java.io.IOException
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CommercesRepositoryImpl
) {
    suspend operator fun invoke(): ResponseCategories<List<String>>{
        return try {
            val commerces = repository.getAllCommerces().filter { it.active }
            if(commerces.isNotEmpty()){
                val allCommerceCategories = commerces.map { commerceVO ->
                    commerceVO.category ?: "OTHER"
                }
                val categoriesGroup = allCommerceCategories.groupBy { category -> category }
                ResponseCategories.Success(categoriesGroup.mapNotNull { name -> name.key })
            }else{
                ResponseCategories.Success(emptyList())
            }
        }catch (httpExc: HttpException) {
            ResponseCategories.Error("Ha ocurrido un error inesperado")
        }catch (ioExc: IOException) {
            ResponseCategories.Error(
                "No se ha podido conectar \ncon el servidor. \nComprueba tu conexión a internet. " +
                        "\nHAZ CLICK AQUÍ"
            )
        }
    }
}