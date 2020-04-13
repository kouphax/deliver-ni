package se.yobriefca.deliveries.filter

import com.natpryce.hamkrest.assertion.assertThat
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.INTERNAL_SERVER_ERROR
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasHeader
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.Test

internal class FiltersSpec {

    @Test
    fun Handle5XX_catches_errors() {
        val chain = Filters.Handle5XX.then { TODO() }
        val response = chain(Request(GET, "/"))
        assertThat(response, hasStatus(INTERNAL_SERVER_ERROR))
    }

    @Test
    fun Handle5XX_passes_non_errors() {
        val chain = Filters.Handle5XX.then { Response(OK) }
        val response = chain(Request(GET, "/"))
        assertThat(response, hasStatus(OK))
    }

    @Test
    fun Handle404_sets_the_body() {
        val chain = Filters.Handle404.then { Response(NOT_FOUND).body("initial body") }
        val response = chain(Request(GET, "/"))
        assertThat(response, hasStatus(NOT_FOUND))
        assertThat(response, hasBody("NOT FOUND"))
    }

    @Test
    fun Handle404_passes_ok_status() {
        val chain = Filters.Handle404.then { Response(OK).body("initial body") }
        val response = chain(Request(GET, "/"))
        assertThat(response, hasStatus(OK))
        assertThat(response, hasBody("initial body"))
    }

    @Test
    fun HandleCORS_sets_the_right_policy() {
        val chain = Filters.Cors.then { Response(OK) }
        val response = chain(Request(GET, "/"))
        assertThat(response, hasHeader("access-control-allow-origin", "*"))
        assertThat(response, hasHeader("access-control-allow-headers", "Content-Type"))
        assertThat(response, hasHeader("access-control-allow-methods", "GET"))
    }
}
