package se.yobriefca.deliveries.api

import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import se.yobriefca.deliveries.service.DatabaseDeliveriesService
import se.yobriefca.deliveries.service.DeliveriesService

class ApiHandler(private val service: DeliveriesService = DatabaseDeliveriesService()) : Router {
    override fun routes(): RoutingHttpHandler =
        routes(
            "/categories" bind CategoriesHandler(service).routes(),
            "/districts" bind DistrictsHandler(service).routes(),
            "/places" bind PlacesHandler(service).routes()
        )
}
