package com.codibly_doko.lookintobitcoin

import com.codibly_doko.lookintobitcoin.dto.LIBChartResponseDto
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.node.ArrayNode

abstract class LIBResponseDeserializer(vc: Class<*>?) : StdDeserializer<LIBChartResponseDto>(vc) {
    companion object {
        const val DROP_FIRST_N = 10
    }

    override fun deserialize(jsonParser: JsonParser, context: DeserializationContext): LIBChartResponseDto {
        val node: JsonNode = jsonParser.codec.readTree(jsonParser)
        val dataArrayNode: ArrayNode = node.get("response").get("props").get("figure").withArray("data")
        for (dataNode in dataArrayNode) {
            val name = dataNode.get("name").asText()
                ?: throw IllegalArgumentException("Missing name node in lookintobitcoin response")
            if (name == getChartName()) {
                val dates = dataNode.withArray<ArrayNode>("x").map { it.asText() }.drop(DROP_FIRST_N)
                val values = dataNode.withArray<ArrayNode>("y").map { it.asDouble() }.drop(DROP_FIRST_N)
                return createDto(name, dates, values)
            }
        }
        throw UnsupportedOperationException("Missing expected data node - " + getChartName())
    }

    abstract fun createDto(name: String, dates: List<String>, values: List<Double>): LIBChartResponseDto

    abstract fun getChartName(): String
}
