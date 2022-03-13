package com.codibly_doko.user.model.mapper

import com.codibly_doko.user.dto.SignupRequestDto
import com.codibly_doko.user.model.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers


@Mapper
interface UserMapper {
    companion object {
        val INSTANCE: UserMapper = Mappers.getMapper(UserMapper::class.java)
    }

    @Mapping(source = "password1", target = "password")
    fun fromSignupRequestDto(signupRequestDto: SignupRequestDto): User
}