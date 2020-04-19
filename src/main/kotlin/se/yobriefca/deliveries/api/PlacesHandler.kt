package se.yobriefca.deliveries.api

import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.lens.Query
import org.http4k.lens.regex
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import se.yobriefca.deliveries.service.DeliveriesService

class PlacesHandler(private val service: DeliveriesService) : Router {

    private val district = Query.regex("^(BT\\d{1,2})$").multi.optional("district")
    private val category = Query.multi.optional("category")

    override fun routes(): RoutingHttpHandler =
        routes(
            "/" bind GET to {
                Response(OK).with(
                    placesListBody of service.places(
                        did = district(it),
                        cid = category(it)
                    )
                )
            })
}
