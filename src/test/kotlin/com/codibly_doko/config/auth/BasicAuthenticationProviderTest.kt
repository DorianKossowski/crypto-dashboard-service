package com.codibly_doko.config.auth

import com.codibly_doko.user.UserRepository
import com.codibly_doko.user.model.User
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.UsernamePasswordCredentials
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import reactor.core.publisher.Mono
import java.util.*

internal class BasicAuthenticationProviderTest {
    companion object {
        private const val MAIL = "mail"
        private const val PASSWORD = "pass"
        private val ID = UUID.randomUUID()
    }

    private val userRepository: UserRepository = mock()
    private val basicAuthenticationProvider = BasicAuthenticationProvider(userRepository)

    @BeforeEach
    internal fun setUp() {
        whenever(userRepository.findByMail(MAIL)).thenReturn(Optional.of(User(ID, MAIL, PASSWORD)))
    }

    @Test
    internal fun shouldReturnSuccess() {
        val result: Mono<AuthenticationResponse?> =
            basicAuthenticationProvider.authenticate(
                null,
                UsernamePasswordCredentials(MAIL, PASSWORD)
            ) as Mono<AuthenticationResponse?>

        assertThat(result.block()?.authentication?.get()?.name).isEqualTo(ID.toString())
    }

    @Test
    internal fun shouldReturnFailureWhenMissingUser() {
        whenever(userRepository.findByMail(MAIL)).thenReturn(Optional.empty())

        val result: Mono<AuthenticationResponse?> =
            basicAuthenticationProvider.authenticate(
                null,
                UsernamePasswordCredentials(MAIL, PASSWORD)
            ) as Mono<AuthenticationResponse?>

        assertFailure(result, "User Not Found")
    }

    private fun assertFailure(result: Mono<AuthenticationResponse?>, message: String) {
        val authenticationResponse = result.block()!!
        assertThat(authenticationResponse.isAuthenticated).isFalse
        assertThat(authenticationResponse.message.get()).isEqualTo(message)
    }

    @Test
    internal fun shouldReturnFailureWhenWrongPassword() {
        val result: Mono<AuthenticationResponse?> =
            basicAuthenticationProvider.authenticate(
                null,
                UsernamePasswordCredentials(MAIL, "wrong")
            ) as Mono<AuthenticationResponse?>

        assertFailure(result, "Credentials Do Not Match")
    }
}