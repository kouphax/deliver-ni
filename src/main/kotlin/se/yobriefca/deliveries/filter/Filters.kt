package se.yobriefca.deliveries.filter

import mu.KotlinLogging
import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.INTERNAL_SERVER_ERROR
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.filter.CorsPolicy
import org.http4k.filter.ServerFilters

private val logger = KotlinLogging.logger { }

object Filters {

    val Tracing = ServerFilters.RequestTracing(
            { request, traces ->
                logger.info {
                    "${traces.traceId.value} REQ ${request.method.name} ${request.uri}"
                }
            },
            { request, response, traces ->
                logger.info {
                    "${traces.traceId.value} RES ${request.method.name} ${request.uri} - ${response.status}"
                }
            }
    )

    val Cors = ServerFilters.Cors(CorsPolicy(
            origins = listOf("*"),
            headers = listOf("Content-Type"),
            methods = listOf(GET)))

    val Handle404 = Filter { handler: HttpHandler ->
        {
            val response = handler(it)
            if (response.status == NOT_FOUND) {
                Response(NOT_FOUND).body("NOT FOUND")
            } else {
                response
            }
        }
    }

    val Handle5XX = Filter { handler: HttpHandler ->
        {
            try {
                handler(it)
            } catch (e: Exception) {
                logger.error("error processing request", e)
                Response(INTERNAL_SERVER_ERROR)
            }
        }
    }
}
