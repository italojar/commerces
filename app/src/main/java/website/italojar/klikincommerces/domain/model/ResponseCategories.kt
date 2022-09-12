package website.italojar.klikincommerces.domain.model

sealed class ResponseCategories<T>(val data: List<String>? = null, val message: String? = null) {
    class Success<T>(data: List<String>) : ResponseCategories<T>(data)
    class Error<T>(message: String, data: List<String>? = null) : ResponseCategories<T>(data, message)
}
