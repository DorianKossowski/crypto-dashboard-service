package com.codibly_doko.lookintobitcoin.dto

import com.codibly_doko.lookintobitcoin.LIBResponseDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(using = LIBBitcoinChartResponseDto.LIBBitcoinResponseDeserializer::class)
class LIBBitcoinChartResponseDto(name: String, dates: List<String>, values: List<Double>) :
    LIBChartResponseDto(name, dates, values) {

    class LIBBitcoinResponseDeserializer : LIBResponseDeserializer(LIBBitcoinChartResponseDto::class.java) {

        override fun createDto(name: String, dates: List<String>, values: List<Double>) =
            LIBBitcoinChartResponseDto(name, dates, values)

        override fun getChartName() = "BTC Price"
    }
}
