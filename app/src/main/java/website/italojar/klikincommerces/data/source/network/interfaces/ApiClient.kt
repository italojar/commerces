package website.italojar.klikincommerces.data.source.network.interfaces

import retrofit2.Response
import retrofit2.http.GET
import website.italojar.klikincommerces.data.model.dto.CommercesData

interface ApiClient {

    @GET("commerces/public")
    suspend fun getAllCommerces(): Response<CommercesData>
}