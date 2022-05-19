package com.codibly_doko.alarm.exception

import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException

class UserAccessDeniedException : HttpStatusException(HttpStatus.FORBIDDEN, "Forbidden")
