package website.italojar.klikincommerces.data.model.dto

data class CommerceDto(
    val active: Boolean,
    val address: Address,
    val category: String,
    val contact: Contact,
    val description: String,
    val distance: Double?,
    val id: String,
    val latitude: Double,
    val logo: LogoX,
    val longitude: Double,
    val name: String,
    val openingHours: String,
    val shortDescription: String,
    val slug: String
)