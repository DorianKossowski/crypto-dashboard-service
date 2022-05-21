package com.codibly_doko.chart.dto.mapper

import com.codibly_doko.chart.dto.ChartDto
import com.codibly_doko.lookintobitcoin.dto.LIBChartResponseDto
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface ChartDtoMapper {
    companion object {
        val INSTANCE: ChartDtoMapper = Mappers.getMapper(ChartDtoMapper::class.java)
    }

    fun fromLIBChartResponseDto(libChartResponseDto: LIBChartResponseDto): ChartDto
}
