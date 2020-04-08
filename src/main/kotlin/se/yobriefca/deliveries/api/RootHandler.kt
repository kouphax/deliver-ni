package se.yobriefca.deliveries.api

import org.http4k.core.Status
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.routing.bind
import org.http4k.routing.routes
import se.yobriefca.deliveries.filter.Filters

class RootHandler : Router {
    override fun routes() =
        Filters.Tracing
            .then(ServerFilters.CatchAll(Status.Companion.INTERNAL_SERVER_ERROR))
            .then(Filters.Cors)
            .then(routes(
                    "/deliveries" bind DeliveriesHandler().routes()
            ))
}

