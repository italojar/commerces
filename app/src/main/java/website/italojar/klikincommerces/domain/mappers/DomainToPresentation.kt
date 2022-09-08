package website.italojar.klikincommerces.domain.mappers

import website.italojar.klikincommerces.domain.model.Commerce
import website.italojar.klikincommerces.presentation.model.CommerceVO

fun Commerce.tovO() = CommerceVO(
    active = this.active,
    address = this.address,
    category = this.category,
    contact = this.contact,
    description = this.description,
    id = this.id,
    latitude = this.latitude,
    logo = this.logo,
    longitude = this.longitude,
    name = this.name,
    openingHours = this.openingHours,
    shortDescription = this.shortDescription,
    slug = this.slug
)