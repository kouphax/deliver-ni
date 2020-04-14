package se.yobriefca.deliveries.api

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.mockk.every
import io.mockk.mockk
import org.http4k.core.ContentType.Companion.APPLICATION_JSON
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status.Companion.INTERNAL_SERVER_ERROR
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Status.Companion.UNPROCESSABLE_ENTITY
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasContentType
import org.http4k.hamkrest.hasHeader
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.Test
import se.yobriefca.deliveries.model.Place
import se.yobriefca.deliveries.model.Position
import se.yobriefca.deliveries.service.DeliveriesService

internal class RootHandlerSpec {
    private val service = mockk<DeliveriesService>()
    private val handler = RootHandler(service)

    @Test
    fun lens_failure_results_in_422() {
        val response = handler.routes()(Request(Method.GET, "/api/places").query("district", "NO"))
        assertThat(response, hasStatus(UNPROCESSABLE_ENTITY))
    }

    @Test
    fun general_failure_results_in_500() {
        every { service.places(any(), any()) } throws RuntimeException("TODO")
        val response = handler.routes()(Request(Method.GET, "/api/places"))
        assertThat(response, hasStatus(INTERNAL_SERVER_ERROR))
    }

    @Test
    fun happy_path_sets_up_everything_and_returns_results() {
        val placesList = listOf(Place(1, "name", Position(1f, 2f)))
        every { service.places(any(), any()) } returns placesList
        val response = handler.routes()(Request(Method.GET, "/api/places"))
        assertThat(response, hasStatus(OK))
        assertThat(response, hasHeader("access-control-allow-origin", "*"))
        assertThat(response, hasHeader("access-control-allow-headers", "Content-Type"))
        assertThat(response, hasHeader("access-control-allow-methods", "GET"))
        assertThat(response, hasContentType(APPLICATION_JSON))
        assertThat(
            response, hasBody(
                placesListBody,
                equalTo(placesList)
            )
        )
    }
}
