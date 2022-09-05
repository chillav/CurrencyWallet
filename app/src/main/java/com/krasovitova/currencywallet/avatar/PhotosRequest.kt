package com.krasovitova.currencywallet.avatar

import com.google.gson.annotations.SerializedName

data class PhotosRequest(
    @SerializedName("photos")
    val root: PhotoRequest?
)

data class PhotoRequest(
    @SerializedName("photo")
    val images: List<ImageModel>?
)

data class ImageModel(
    @SerializedName("id")
    val id: String?,
    @SerializedName("farm")
    val farm: Int?,
    @SerializedName("server")
    val server: String?,
    @SerializedName("secret")
    val secret: String?
)

fun ImageModel.mapToUi(): ImageUi? {
    return if (id != null && farm != null && server != null && secret != null) {
        ImageUi(
            id = id,
            farm = farm,
            server = server,
            secret = secret
        )
    } else null
}

fun PhotosRequest?.mapToUi(): List<ImageUi> {
    return this?.root?.images?.mapNotNull {
        it.mapToUi()
    }.orEmpty()
}