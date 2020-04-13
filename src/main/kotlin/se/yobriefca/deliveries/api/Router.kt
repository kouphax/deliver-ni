package se.yobriefca.deliveries.api

import org.http4k.core.Body
import org.http4k.format.Jackson.auto
import org.http4k.routing.RoutingHttpHandler
import se.yobriefca.deliveries.model.Place

interface Router {
    fun routes(): RoutingHttpHandler
}

public val placesListBody = Body.auto<List<Place>>().toLens()
