package website.italojar.klikincommerces.domain.model

sealed class ResponseCommerces<T>(val data: List<Commerce>? = null, val message: String? = null) {
    class Success<T>(data: List<Commerce>) : ResponseCommerces<T>(data)
    class Error<T>(message: String, data: List<Commerce>? = null) : ResponseCommerces<T>(data, message)
}
