package website.italojar.klikincommerces.domain.usecase

import retrofit2.HttpException
import website.italojar.klikincommerces.data.repository.CommercesRepositoryImpl
import website.italojar.klikincommerces.domain.model.Commerce
import website.italojar.klikincommerces.domain.model.ResponseCommerces
import java.io.IOException
import javax.inject.Inject

class GetCommercesByCategoryUseCase @Inject constructor(
    private val repository: CommercesRepositoryImpl
) {
    suspend operator fun invoke(category: String): ResponseCommerces<List<Commerce>> {
        try {
            val commerces = repository.getAllCommerces()
            return if(commerces.isNotEmpty())
                ResponseCommerces.Success(commerces.filter {
                    it.active && it.category == category
                })
            else
                ResponseCommerces.Success(emptyList())
        }catch (httpExc: HttpException) {
            return ResponseCommerces.Error("Ha ocurrido un error inesperado")
        }catch (ioExc: IOException) {
            return ResponseCommerces.Error(
                "No se ha podido conectar \ncon el servidor. \nComprueba tu conexión a internet. " +
                        "\nHAZ CLICK AQUÍ"
            )
        }
    }
}