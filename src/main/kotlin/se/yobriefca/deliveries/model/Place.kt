package se.yobriefca.deliveries.model

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

data class Position(val lat: Float, val lng: Float)

data class Place(val id: Int, val name: String, val postion: Position)

object Places : Table("places") {

    val id = integer("id")
    val name = varchar("name", 100)
    val lat = float("lat")
    val lng = float("lng")

    override val primaryKey = PrimaryKey(id)

    fun all(): List<Place> {
        return selectAll().map(::toPlace)
    }

    fun area(area: String): List<Place> {
        return (Places innerJoin DeliveryAreas).select {
            DeliveryAreas.area eq area
        }.map(::toPlace)
    }

    private fun toPlace(row: ResultRow): Place =
            Place(
                    row[id],
                    row[name],
                    Position(
                            row[lat],
                            row[lng]
                    )
            )
}

object DeliveryAreas : Table("delivery_areas") {
    val pid = integer("pid") references Places.id
    val area = varchar("area", 5)
}
