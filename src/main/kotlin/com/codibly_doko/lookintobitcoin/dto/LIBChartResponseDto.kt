package com.codibly_doko.lookintobitcoin.dto

import com.codibly_doko.lookintobitcoin.LIBResponseDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(using = LIBResponseDeserializer::class)
abstract class LIBChartResponseDto(
    val name: String,
    val dates: List<String>,
    val values: List<Double>
)