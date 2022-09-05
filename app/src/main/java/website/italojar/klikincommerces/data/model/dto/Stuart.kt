package website.italojar.klikincommerces.data.model.dto

data class Stuart(
    val active: Boolean,
    val apiKey: String,
    val apiSecret: String,
    val customErrorManagement: Boolean,
    val customErrorMessage: String,
    val forceDeliverySize: ForceDeliverySize
)