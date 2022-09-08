package website.italojar.klikincommerces.domain.model

import website.italojar.klikincommerces.data.model.dto.Address
import website.italojar.klikincommerces.data.model.dto.Contact
import website.italojar.klikincommerces.data.model.dto.LogoX

data class Commerce(
    val active: Boolean,
    val address: Address,
    val category: String?,
    val contact: Contact,
    val description: String?,
    val id: String,
    val latitude: Double,
    val logo: LogoX?,
    val longitude: Double,
    val name: String,
    val openingHours: String?,
    val shortDescription: String?,
    val slug: String
)
