package com.codibly_doko.lookintobitcoin.dto

import com.codibly_doko.lookintobitcoin.LIBResponseDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(using = LIBReserveRiskChartResponseDto.LIBReserveRiskResponseDeserializer::class)
class LIBReserveRiskChartResponseDto(name: String, dates: List<String>, values: List<Double>) :
    LIBChartResponseDto(name, dates, values) {

    class LIBReserveRiskResponseDeserializer : LIBResponseDeserializer(LIBReserveRiskChartResponseDto::class.java) {

        override fun createDto(name: String, dates: List<String>, values: List<Double>) =
            LIBReserveRiskChartResponseDto(name, dates, values)

        override fun getChartName() = "Reserve Risk"
    }
}
