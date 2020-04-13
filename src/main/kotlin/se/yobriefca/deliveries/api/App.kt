package se.yobriefca.deliveries.api

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.http4k.core.Body
import org.http4k.format.Jackson.auto
import org.http4k.routing.RoutingHttpHandler
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.jetbrains.exposed.sql.Database
import se.yobriefca.deliveries.model.Place

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

interface Router {
    fun routes(): RoutingHttpHandler
}

val placesListBody = Body.auto<List<Place>>().toLens()
