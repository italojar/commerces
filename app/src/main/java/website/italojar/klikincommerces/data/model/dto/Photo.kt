package website.italojar.klikincommerces.data.model.dto

data class Photo(
    val _id: String,
    val format: String,
    val thumbnails: Thumbnails,
    val url: String
)