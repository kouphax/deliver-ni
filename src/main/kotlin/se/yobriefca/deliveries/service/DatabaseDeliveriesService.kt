package se.yobriefca.deliveries.service

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import se.yobriefca.deliveries.model.Category
import se.yobriefca.deliveries.model.Place
import se.yobriefca.deliveries.model.Position

class DatabaseDeliveriesService : DeliveriesService {

    object Schema {

        object Places : Table("places") {
            val id = integer("id")
            val name = varchar("name", 100)
            val lat = float("lat")
            val lng = float("lng")

            override val primaryKey = PrimaryKey(id)
        }

        object Districts : Table("districts") {
            val id = varchar("id", 5)

            override val primaryKey = PrimaryKey(id)
        }

        object PlacesDeliveryAreas : Table("places_delivery_areas") {
            val pid = integer("pid") references Places.id
            val area = varchar("area", 5) references Districts.id
        }

        object PlacesCategories : Table("places_categories") {
            val pid = integer("pid") references Places.id
            val cid = varchar("cid", 100) references Categories.id
        }

        object Categories : Table("categories") {
            val id = varchar("id", 100)
            val name = varchar("name", 100)
        }
    }

    override fun places(did: String?, cid: String?): List<Place> =
        transaction {
            if (did != null && cid != null) {
                (Schema.Places innerJoin Schema.PlacesDeliveryAreas innerJoin Schema.PlacesCategories)
                    .select {
                        (Schema.PlacesCategories.cid eq cid) and
                            (Schema.PlacesDeliveryAreas.area eq did)
                    }
                    .map { it.asPlace }
            } else if (did != null && cid == null) {
                (Schema.Places innerJoin Schema.PlacesDeliveryAreas)
                    .select {
                        Schema.PlacesDeliveryAreas.area eq did
                    }
                    .map { it.asPlace }
            } else if (did == null && cid != null) {
                (Schema.Places innerJoin Schema.PlacesCategories)
                    .select {
                        Schema.PlacesCategories.cid eq cid
                    }
                    .map { it.asPlace }
            } else {
                Schema.Places.selectAll()
                    .map { it.asPlace }
            }
        }

    override fun districts(): List<String> =
        transaction {
            Schema.Districts
                .selectAll()
                .map { it[Schema.Districts.id] }
        }

    override fun categories(): List<Category> =
        transaction {
            Schema.Categories
                .selectAll()
                .map { it.asCategory }
        }

    private val ResultRow.asPlace: Place
        get() = this.run {
            Place(
                get(Schema.Places.id),
                get(Schema.Places.name),
                Position(
                    get(Schema.Places.lat),
                    get(Schema.Places.lng)
                )
            )
        }

    private val ResultRow.asCategory: Category
        get() = this.run {
            Category(
                get(Schema.Categories.id),
                get(Schema.Categories.name)
            )
        }
}
