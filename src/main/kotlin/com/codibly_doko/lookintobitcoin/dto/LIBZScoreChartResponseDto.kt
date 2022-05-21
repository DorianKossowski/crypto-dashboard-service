package com.codibly_doko.lookintobitcoin.dto

import com.codibly_doko.lookintobitcoin.LIBResponseDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(using = LIBZScoreChartResponseDto.LIBZScoreResponseDeserializer::class)
class LIBZScoreChartResponseDto(name: String, dates: List<String>, values: List<Double>) :
    LIBChartResponseDto(name, dates, values) {

    class LIBZScoreResponseDeserializer : LIBResponseDeserializer(LIBZScoreChartResponseDto::class.java) {

        override fun createDto(name: String, dates: List<String>, values: List<Double>) =
            LIBZScoreChartResponseDto(name, dates, values)

        override fun getChartName() = "Z-Score"
    }
}
