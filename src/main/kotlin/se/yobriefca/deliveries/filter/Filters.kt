package se.yobriefca.deliveries.filter

import mu.KotlinLogging
import org.http4k.core.Method
import org.http4k.filter.CorsPolicy
import org.http4k.filter.ServerFilters

private val logger = KotlinLogging.logger {  }

object Filters {

    val Tracing = ServerFilters.RequestTracing(
            { request, traces ->
                logger.info {
                    "${traces.traceId} REQ ${request.method.name} ${request.uri}"
                }
            },
            { request, response, traces ->
                logger.info {
                    "${traces.traceId} RES ${request.method.name} ${request.uri} - ${response.status}"
                }
            }
    )

    val Cors = ServerFilters.Cors(CorsPolicy(
            origins = listOf("*"),
            headers = listOf("Content-Type"),
            methods = listOf(Method.GET)))
}

