package website.italojar.klikincommerces.presentation.model

import website.italojar.klikincommerces.data.model.dto.Address
import website.italojar.klikincommerces.data.model.dto.Contact
import website.italojar.klikincommerces.data.model.dto.LogoX

data class CommerceVO(
    val active: Boolean,
    val address: Address,
    val category: String?,
    val contact: Contact,
    val description: String?,
    var distance: Double?,
    val id: String,
    val latitude: Double,
    val logo: LogoX?,
    val longitude: Double,
    val name: String,
    val openingHours: String?,
    val shortDescription: String?,
    val slug: String
)
