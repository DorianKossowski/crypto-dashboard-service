package com.codibly_doko.alarm.exception

import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException

class MissingResourceException(resourceId: String) :
    HttpStatusException(HttpStatus.NOT_FOUND, "Missing resource with id $resourceId")