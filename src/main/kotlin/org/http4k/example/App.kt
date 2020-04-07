package org.http4k.example

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer


fun main(args: Array<String>) {
    val app: HttpHandler = routes("/" bind GET to { Response(OK).body("Hello Heroku!") })

    app.asServer(Jetty(args.port)).start().block()
}

private val Array<String>.port: Int get() = firstOrNull()?.toInt() ?: 5000
