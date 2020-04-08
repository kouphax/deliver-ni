package se.yobriefca.deliveries.api

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.jetbrains.exposed.sql.Database

private val Array<String>.port: Int get() = firstOrNull()?.toInt() ?: 5000


fun main(args: Array<String>) {
    Database.connect(
            HikariDataSource(
                    HikariConfig().apply {
                        driverClassName = "org.postgresql.Driver"
                        jdbcUrl = "jdbc:postgresql://localhost/jamhughes"
                        username = "jamhughes"
                        password = ""
                        validate()
                    }
            )
    )

    RootHandler().routes().asServer(Jetty(args.port)).start().block()
}