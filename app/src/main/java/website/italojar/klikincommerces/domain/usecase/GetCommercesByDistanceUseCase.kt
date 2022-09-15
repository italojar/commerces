package website.italojar.klikincommerces.domain.usecase

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import retrofit2.HttpException
import website.italojar.klikincommerces.data.repository.CommercesRepositoryImpl
import website.italojar.klikincommerces.domain.model.Commerce
import website.italojar.klikincommerces.domain.model.ResponseCommerces
import java.io.IOException
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject
import kotlin.math.roundToInt

class GetCommercesByDistanceUseCase @Inject constructor(
    private val repository: CommercesRepositoryImpl
) {
    suspend operator fun invoke(distanceSelected: Int, latLang: LatLng): ResponseCommerces<Commerce> {
        try {
            val commercesMutableList: MutableList<Commerce> = emptyList<Commerce>().toMutableList()
            var distance: Double
            val commerces = repository.getAllCommerces()
            val commercesActives = commerces.filter { it.active }
            commercesActives.forEach { commerce ->
                distance = SphericalUtil
                    .computeDistanceBetween(
                        LatLng(commerce.latitude, commerce.longitude),
                        LatLng(latLang.latitude, latLang.longitude)
                    )
                if (distance <= distanceSelected) {
                    val currentDistance = BigDecimal(distance/1000).setScale(2, RoundingMode.FLOOR)
                    commerce.distance = currentDistance.toDouble()
                    commercesMutableList.add(commerce)
                }
            }
            return if(commerces.isNotEmpty()) {
                ResponseCommerces.Success(commercesMutableList)
            }else { ResponseCommerces.Success(emptyList()) }

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