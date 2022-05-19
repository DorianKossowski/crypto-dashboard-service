package com.codibly_doko.user

import com.codibly_doko.user.dto.SignupRequestDto
import com.codibly_doko.user.dto.UserDto
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller
class UserController(private val userService: UserService) {

    @Post("signup")
    fun signup(@Body signupRequestDto: SignupRequestDto): UserDto {
        return userService.signup(signupRequestDto)
    }
}
