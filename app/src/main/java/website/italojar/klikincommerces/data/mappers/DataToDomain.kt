package website.italojar.klikincommerces.data.mappers

import website.italojar.klikincommerces.data.model.dto.CommerceDto
import website.italojar.klikincommerces.domain.model.Commerce

fun CommerceDto.toDomain() = Commerce(
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