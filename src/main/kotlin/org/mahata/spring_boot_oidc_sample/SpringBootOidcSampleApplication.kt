package org.mahata.spring_boot_oidc_sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootOidcSampleApplication

fun main(args: Array<String>) {
	runApplication<SpringBootOidcSampleApplication>(*args)
}
