package website.italojar.klikincommerces.domain.model

sealed class ResponseCommerces<T>(
    val data: List<T>? = null,
    val message: String? = null,
) {
    class Success<T>(data: List<T>) : ResponseCommerces<T>(data)
    class Error<T>(message: String, data: List<T>? = null) : ResponseCommerces<T>(data,  message)
}
