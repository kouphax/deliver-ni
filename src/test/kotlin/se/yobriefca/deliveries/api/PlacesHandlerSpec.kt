package se.yobriefca.deliveries.api

import com.natpryce.hamkrest.assertion.assertThat
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.hamkrest.hasStatus
import org.http4k.lens.LensFailure
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import se.yobriefca.deliveries.service.DeliveriesService

internal class PlacesHandlerSpec {

    private val service = mockk<DeliveriesService>()
    private val handler = PlacesHandler(service)

    @BeforeEach
    fun beforeEach() {
        clearMocks(service)
        every { service.places(any(), any()) } returns emptyList()
    }

    @Test
    fun PlacesHandler_with_no_query_string_searchs_for_everything() {
        val response = handler.routes()(Request(GET, "/"))
        verify { service.places() }
        assertThat(response, hasStatus(OK))
    }

    @Test
    fun PlacesHandler_will_search_for_district_when_asked() {
        val response = handler.routes()(Request(GET, "/")
            .query("district", "BT8"))
        verify { service.places(did = "BT8") }
        assertThat(response, hasStatus(OK))
    }

    @Test
    fun PlacesHandler_will_validate_postcode() {
        assertThrows<LensFailure> {
            handler.routes()(Request(GET, "/")
                .query("district", "NO"))
        }
    }

    @Test
    fun PlacesHandler_will_search_for_category_when_asked() {
        val response = handler.routes()(Request(GET, "/")
            .query("category", "bakery"))
        verify { service.places(cid = "bakery") }
        assertThat(response, hasStatus(OK))
    }

    @Test
    fun PlacesHandler_will_search_for_category_and_distrcit_when_asked() {
        val response = handler.routes()(Request(GET, "/")
            .query("category", "bakery")
            .query("district", "BT8"))
        verify { service.places("BT8", "bakery") }
        assertThat(response, hasStatus(OK))
    }
}
