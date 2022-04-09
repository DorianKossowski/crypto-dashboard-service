package com.codibly_doko.lookintobitcoin.dto

import com.codibly_doko.lookintobitcoin.LIBResponseDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal abstract class LIBChartResponseDtoTest(val objectMapper: ObjectMapper = ObjectMapper()) {

    @Test
    internal fun shouldDeserialize() {
        val skippedX = "\"b\", ".repeat(LIBResponseDeserializer.Companion.DROP_FIRST_N)
        val skippedY = "2, ".repeat(LIBResponseDeserializer.Companion.DROP_FIRST_N)
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
                                    "name": "${getName()}", "x": [ $skippedX "c" ], "y": [ $skippedY 3 ]
                                }
                            ]
                        }
                    }
                }
            }
        """.trimIndent()

        val dto = deserializationFunction().invoke(input)

        assertThat(dto)
            .returns(getName(), LIBChartResponseDto::name)
            .returns(listOf("c"), LIBChartResponseDto::dates)
            .returns(listOf(3.0), LIBChartResponseDto::values);
    }

    abstract fun getName(): String

    abstract fun deserializationFunction(): (String) -> LIBChartResponseDto
}