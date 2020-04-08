package se.yobriefca.deliveries.service

import org.jetbrains.exposed.sql.transactions.transaction
import se.yobriefca.deliveries.model.Place
import se.yobriefca.deliveries.model.Places

interface DeliveriesService {
    fun all(): Array<Place>
    fun area(area: String): Array<Place>
}

class DatabaseDeliveriesService: DeliveriesService {
    override fun all(): Array<Place> =
        transaction {
            Places.all().toTypedArray()
        }

    override fun area(area: String): Array<Place> =
        transaction {
            Places.area(area).toTypedArray()
        }
}