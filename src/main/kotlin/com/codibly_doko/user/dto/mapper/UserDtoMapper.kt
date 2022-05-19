package com.codibly_doko.user.dto.mapper

import com.codibly_doko.user.dto.UserDto
import com.codibly_doko.user.model.User
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface UserDtoMapper {
    companion object {
        val INSTANCE: UserDtoMapper = Mappers.getMapper(UserDtoMapper::class.java)
    }

    fun fromUser(user: User): UserDto
}
