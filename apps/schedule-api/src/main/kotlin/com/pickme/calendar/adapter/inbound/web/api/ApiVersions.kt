package com.pickme.calendar.adapter.inbound.web.api

object ApiVersions {
    const val V1 = "v1"
    const val V2 = "v2"
}

object ApiPaths {
    const val API = "/api"
    const val V2 = "$API/${ApiVersions.V2}"
}
