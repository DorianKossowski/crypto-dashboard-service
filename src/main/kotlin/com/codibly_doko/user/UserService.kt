package com.codibly_doko.user

import com.codibly_doko.user.dto.SignupRequestDto
import com.codibly_doko.user.dto.UserDto
import com.codibly_doko.user.dto.mapper.UserDtoMapper
import com.codibly_doko.user.model.mapper.UserMapper
import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import jakarta.inject.Singleton

@Singleton
class UserService(private val userRepository: UserRepository) {

    fun signup(signupRequestDto: SignupRequestDto): UserDto {
        if (signupRequestDto.password1 != signupRequestDto.password2) {
            throw HttpStatusException(HttpStatus.BAD_REQUEST, "Inconsistent new user passwords")
        }
        userRepository.findByMail(signupRequestDto.mail)
            .ifPresent { throw HttpStatusException(HttpStatus.BAD_REQUEST, "User with provided mail already exists") }
        val newUser = userRepository.save(UserMapper.INSTANCE.fromSignupRequestDto(signupRequestDto))
        return UserDtoMapper.INSTANCE.fromUser(newUser)
    }
}
