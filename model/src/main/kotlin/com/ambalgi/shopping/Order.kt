package com.ambalgi.shopping

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = Order.mongoCollection)
data class Order(

    @Id
    val id: String,

    val itemList: List<ItemsToBePurchased>,

    val timeStamp: LocalDateTime = LocalDateTime.now()
)
 {
    companion object {
        private const val collection = "orders"
        const val mongoCollection = collection
    }
}

data class ItemsToBePurchased(
    val product: Product,

    val quantity: Int
)