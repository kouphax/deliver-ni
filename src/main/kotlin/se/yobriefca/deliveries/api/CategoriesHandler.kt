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
import se.yobriefca.deliveries.model.Category
import se.yobriefca.deliveries.service.DeliveriesService

class CategoriesHandler(private val service: DeliveriesService) : Router {

    private val cid = Path.fixed("cid")

    override fun routes(): RoutingHttpHandler =
        routes(
            "/" bind GET to {
                Response(OK).with(
                    Body.auto<List<Category>>().toLens() of service.categories()
                )
            },
            "/{cid}" bind GET to { request ->
                val cid = cid(request)
                Response(OK).with(
                    placesListBody of service.places(cid = cid)
                )
            })
}
