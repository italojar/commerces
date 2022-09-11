package website.italojar.klikincommerces.presentation.mappers

import website.italojar.klikincommerces.presentation.model.CommerceDetailVO
import website.italojar.klikincommerces.presentation.model.CommerceVO

fun CommerceVO.toDetail() = CommerceDetailVO(
    address = this.address.street,
    category = this.category,
    contact = this.contact.phone,
    description = this.description,
    latitude = this.latitude,
    logo = this.logo?.url,
    longitude = this.longitude,
    name = this.name,
    openingHours = this.openingHours
)