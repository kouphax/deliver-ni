package se.yobriefca.deliveries.api

import org.http4k.routing.RoutingHttpHandler

interface Router {
    fun routes() : RoutingHttpHandler
}