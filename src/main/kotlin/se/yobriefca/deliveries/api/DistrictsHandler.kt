package se.yobriefca.deliveries.api

import org.http4k.core.Body
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.lens.Path
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import se.yobriefca.deliveries.service.DeliveriesService

class DistrictsHandler(private val service: DeliveriesService) : Router {

    private val district = Path.fixed("district")

    override fun routes(): RoutingHttpHandler =
        routes(
            "/" bind GET to {
                Response(OK).with(
                    Body.auto<List<String>>().toLens() of service.districts()
                )
            },
            "/{district}" bind GET to { request ->
                val did = district(request)
                Response(OK).with(
                    placesListBody of service.places(did = did)
                )
            })
}
