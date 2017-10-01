package br.com.rfsbarreto

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class WishlistApplication

fun main(args: Array<String>) {
    SpringApplication.run(WishlistApplication::class.java, *args)
}
