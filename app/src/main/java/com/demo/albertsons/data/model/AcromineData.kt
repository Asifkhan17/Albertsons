package com.demo.albertsons.data.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class AcromineData {
    @SerializedName("sf")
    @Expose
    var sf: String? = null

    @SerializedName("lfs")
    @Expose
    var lfs: List<Lf>? = null
}