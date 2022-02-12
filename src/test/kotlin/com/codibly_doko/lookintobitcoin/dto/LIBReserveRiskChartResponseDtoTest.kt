package com.codibly_doko.lookintobitcoin.dto

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class LIBReserveRiskChartResponseDtoTest {
    private val objectMapper = ObjectMapper()

    @Test
    internal fun shouldDeserialize() {
        val input = """
            {
                "response": {
                    "props": {
                        "figure": {
                            "data": [
                                {
                                    "name": "Some", "x": [ "a" ], "y": [ 1 ]
                                },
                                {
                                    "name": "Reserve Risk", "x": [ "b" ], "y": [ 2 ]
                                }
                            ]
                        }
                    }
                }
            }
        """.trimIndent()

        val dto = objectMapper.readValue(input, LIBReserveRiskChartResponseDto::class.java)

        Assertions.assertThat(dto)
            .returns("Reserve Risk", LIBReserveRiskChartResponseDto::name)
            .returns(listOf("b"), LIBReserveRiskChartResponseDto::dates)
            .returns(listOf(2.0), LIBReserveRiskChartResponseDto::values);
    }
}