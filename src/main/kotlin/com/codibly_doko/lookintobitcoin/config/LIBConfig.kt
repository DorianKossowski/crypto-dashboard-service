package com.codibly_doko.lookintobitcoin.config

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Requires

@ConfigurationProperties(LIBConfig.PREFIX)
@Requires(property = LIBConfig.PREFIX)
class LIBConfig {
    companion object {
        const val PREFIX = "lookIntoBitcoin"
    }

    var url: String? = null
}
