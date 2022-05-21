package com.codibly_doko.user

import com.codibly_doko.user.model.User
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, UUID> {
    fun findByMail(mail: String): Optional<User>
}
