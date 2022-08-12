package com.example.example

import com.example.maydcodingchallenge.data.response.model.ResultUrlData
import com.google.gson.annotations.SerializedName


data class ShortenUrlResponse(
    @SerializedName("ok") var ok: Boolean? = null,
    @SerializedName("result") var result: ResultUrlData? = ResultUrlData()
)