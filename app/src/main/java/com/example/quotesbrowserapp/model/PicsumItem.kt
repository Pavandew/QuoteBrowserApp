package com.example.quotesbrowserapp.model

import com.google.gson.annotations.SerializedName

data class PicsumItem(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @SerializedName("download_url")
    val downloadUrl: String// make it nullable
)