package com.ambalgi.shopping

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = Receipt.mongoCollection)
data class Receipt(

    @Id
    val id: String = "",

    val orderId: String,

    val purchasedList: List<Purchased>,

    val totalTax: Double = String.format("%.2f").toDouble(),

    val totalPrice: Double = String.format("%.2f").toDouble(),

    val timeStamp: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        private const val collection = "receipts"
        const val mongoCollection = collection
    }
}

data class Purchased(

    val product: Product,

    val quantity: Int,

    val tax: Double,

    val salesTax: Double,

    val finalPrice: Double
)