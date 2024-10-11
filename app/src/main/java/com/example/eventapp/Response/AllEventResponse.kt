package com.example.eventapp.Response

import com.google.gson.annotations.SerializedName

data class AllEventResponse(

    @field:SerializedName("listEvents")
    val listEvents: List<ListEventsItem>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class ListEventsItem(


    @field:SerializedName("mediaCover")
    val mediaCover: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    )
