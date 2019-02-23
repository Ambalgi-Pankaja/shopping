package com.ambalgi.shopping

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = Product.mongoCollection)
data class Product(

    @Id
    val id: String? = null,

    val productName: String,

    val price: Double = String.format("%.2f").toDouble(),

    val category: Category?,

    val imported: Boolean
) {
    companion object {
        private const val collection = "products"
        const val mongoCollection = collection
    }
}

enum class Category {
    Book,
    Food,
    Medical
}
