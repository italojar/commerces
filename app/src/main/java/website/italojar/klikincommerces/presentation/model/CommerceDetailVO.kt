package website.italojar.klikincommerces.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommerceDetailVO(
    val address: String,
    val category: String?,
    val contact: String,
    val description: String?,
    val latitude: Double,
    val logo: String?,
    val longitude: Double,
    val name: String,
    val openingHours: String?
): Parcelable
