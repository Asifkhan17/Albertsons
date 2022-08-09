package com.demo.albertsons.data.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class Lf {
    @SerializedName("lf")
    @Expose
    var lf: String? = null

    @SerializedName("freq")
    @Expose
    var freq: Int? = null

    @SerializedName("since")
    @Expose
    var since: Int? = null

    @SerializedName("vars")
    @Expose
    var vars: List<Var>? = null
}