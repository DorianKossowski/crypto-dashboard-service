package com.codibly_doko.user

import com.codibly_doko.user.dto.SignupRequestDto
import com.codibly_doko.user.dto.UserDto
import com.codibly_doko.user.model.User
import io.micronaut.http.exceptions.HttpStatusException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

class UserServiceTest {
    companion object {
        private const val MAIL = "mail"
        private const val PASSWORD = "pass"
        private val ID = UUID.randomUUID()
    }

    private val userRepository: UserRepository = mock()
    private val userService = UserService(userRepository)

    @Test
    internal fun shouldCreateUser() {
        whenever(userRepository.findByMail(MAIL)).thenReturn(Optional.empty())
        whenever(userRepository.save(User(null, MAIL, PASSWORD))).thenReturn(User(ID, MAIL, PASSWORD))

        val userDto = userService.signup(SignupRequestDto(MAIL, PASSWORD, PASSWORD))

        assertThat(userDto)
            .returns(ID, UserDto::id)
            .returns(MAIL, UserDto::mail)
    }

    @Test
    internal fun shouldThrowWhenInvalidPasswords() {
        assertThatThrownBy { userService.signup(SignupRequestDto(MAIL, PASSWORD, "PASSWORD")) }
            .isExactlyInstanceOf(HttpStatusException::class.java)
            .hasMessage("Inconsistent new user passwords")
    }

    @Test
    internal fun shouldThrowWhenAlreadyExists() {
        whenever(userRepository.findByMail(MAIL)).thenReturn(Optional.of(User(ID, MAIL, PASSWORD)))

        assertThatThrownBy { userService.signup(SignupRequestDto(MAIL, PASSWORD, PASSWORD)) }
            .isExactlyInstanceOf(HttpStatusException::class.java)
            .hasMessage("User with provided mail already exists")
    }
}
