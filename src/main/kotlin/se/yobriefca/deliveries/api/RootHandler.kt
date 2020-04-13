package se.yobriefca.deliveries.api

import org.http4k.core.then
import org.http4k.routing.bind
import org.http4k.routing.routes
import se.yobriefca.deliveries.filter.Filters
import se.yobriefca.deliveries.service.DatabaseDeliveriesService
import se.yobriefca.deliveries.service.DeliveriesService

class RootHandler(private val service: DeliveriesService = DatabaseDeliveriesService()) : Router {
    override fun routes() =
        Filters.Tracing
            .then(Filters.Cors)
            .then(Filters.Handle404)
            .then(Filters.Handle5XX)
            .then(Filters.HandleValidationFailure)
            .then(
                routes(
                    "/categories" bind CategoriesHandler(service).routes(),
                    "/districts" bind DistrictsHandler(service).routes(),
                    "/places" bind PlacesHandler(service).routes()
                )
            )
}
