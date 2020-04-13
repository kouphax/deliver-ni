package se.yobriefca.deliveries.service

import se.yobriefca.deliveries.model.Category
import se.yobriefca.deliveries.model.Place

interface DeliveriesService {
    fun districts(): List<String>
    fun categories(): List<Category>
    fun places(did: String? = null, cid: String? = null): List<Place>
}
