package com.codibly_doko.config.auth

import com.codibly_doko.user.UserRepository
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono

@Singleton
class BasicAuthenticationProvider(private val userRepository: UserRepository) : AuthenticationProvider {
    override fun authenticate(
        httpRequest: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<*, *>
    ): Publisher<AuthenticationResponse?> {
        return Mono.just(
            authenticate(
                authenticationRequest.identity as String,
                authenticationRequest.secret as String
            )
        )
    }

    private fun authenticate(mail: String, password: String) =
        userRepository.findByMail(mail).map { user ->
            if (user.password == password) {
                AuthenticationResponse.success(user.id.toString())
            } else {
                AuthenticationFailed(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH)
            }
        }.orElse(AuthenticationFailed(AuthenticationFailureReason.USER_NOT_FOUND))
}
