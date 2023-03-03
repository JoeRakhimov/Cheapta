package com.cheapta.app.screens.destinations

import com.google.gson.annotations.SerializedName

data class Location(

	@field:SerializedName("country_code")
	val countryCode: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("flag")
	val flag: String? = null,

)
