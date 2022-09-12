package website.italojar.klikincommerces.domain.usecase

import retrofit2.HttpException
import website.italojar.klikincommerces.data.repository.CommercesRepositoryImpl
import website.italojar.klikincommerces.domain.model.Commerce
import website.italojar.klikincommerces.domain.model.ResponseCommerces
import java.io.IOException
import javax.inject.Inject

class GetCommercesUseCase @Inject constructor(
    private val repository: CommercesRepositoryImpl
) {
    suspend operator fun invoke(): ResponseCommerces<List<Commerce>>{
        return try {
            val commerces = repository.getAllCommerces()
            if(commerces.isNotEmpty())
                ResponseCommerces.Success(commerces.filter { it.active })
            else
                ResponseCommerces.Success(emptyList())
        }catch (httpExc: HttpException) {
            ResponseCommerces.Error("Ha ocurrido un error inesperado")
        }catch (ioExc: IOException) {
            ResponseCommerces.Error(
                "No se ha podido conectar \ncon el servidor. \nComprueba tu conexión a internet. " +
                        "\nHAZ CLICK AQUÍ"
            )
        }
    }
}