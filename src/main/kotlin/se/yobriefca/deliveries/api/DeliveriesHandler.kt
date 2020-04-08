package se.yobriefca.deliveries.api

import org.http4k.core.Body
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import se.yobriefca.deliveries.model.Place
import se.yobriefca.deliveries.service.DatabaseDeliveriesService
import se.yobriefca.deliveries.service.DeliveriesService

class DeliveriesHandler(private val service: DeliveriesService = DatabaseDeliveriesService()) : Router {

    private val body = Body.auto<Array<Place>>().toLens()

    override fun routes(): RoutingHttpHandler =
            routes(
                    "/" bind GET to {
                        Response(OK).with(
                                body of service.all()
                        )
                    },
                    "/{area}" bind GET to { request ->
                        val area = request.path("area")!!
                        Response(OK).with(
                                body of service.area(area)
                        )
                    })
}
