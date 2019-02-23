package com.ambalgi.shopping

import com.ambalgi.shopping.order.OrderRepository
import com.ambalgi.shopping.product.ProductRepository
import com.ambalgi.shopping.receipt.ReceiptRepository
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.UUID

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
abstract class TestBase {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Autowired
    lateinit var receiptRepository: ReceiptRepository

    @Before
    fun setUpTestData() {
        productRepository.deleteAll()
        productRepository.saveAll(testProduct())
        orderRepository.deleteAll()
        orderRepository.saveAll(testOrder())
        receiptRepository.deleteAll()
        receiptRepository.saveAll(testReceipt())
    }

    fun testProduct(): List<Product> {

        // index 0
        val book = Product(
            id = UUID.randomUUID().toString(),
            productName = "book",
            price = 12.49,
            category = Category.Book,
            imported = false
        )

        // index 1
        val chocolate = Product(
            id = UUID.randomUUID().toString(),
            productName = "chocolate bar",
            price = 0.85,
            category = Category.Food,
            imported = false
        )

        // index 2
        val medicines = Product(
            id = UUID.randomUUID().toString(),
            productName = "headache Pills",
            price = 9.75,
            category = Category.Medical,
            imported = false
        )

        // index 3
        val musicCD = Product(
            id = UUID.randomUUID().toString(),
            productName = "music CD",
            price = 14.99,
            category = null,
            imported = false
        )

        // index 4
        val perfume = Product(
            id = UUID.randomUUID().toString(),
            productName = "perfume",
            price = 18.99,
            category = null,
            imported = false
        )

        // index 5
        val importedChocolate = Product(
            id = UUID.randomUUID().toString(),
            productName = "imported chocolate",
            price = 10.00,
            category = Category.Food,
            imported = true
        )

        // index 6
        val importedPerfume = Product(
            id = UUID.randomUUID().toString(),
            productName = "imported perfume",
            price = 47.50,
            category = null,
            imported = true
        )

        // index 7
        val boxOfChocolate = Product(
            id = UUID.randomUUID().toString(),
            productName = "imported box Of Chocolates",
            price = 11.25,
            category = Category.Food,
            imported = true
        )

        // index 8
        val importedPerfume2 = Product(
            id = UUID.randomUUID().toString(),
            productName = "imported bottle of perfume",
            price = 27.99,
            category = null,
            imported = true
        )

        return listOf(book, chocolate, medicines, musicCD, perfume, importedChocolate, importedPerfume, boxOfChocolate, importedPerfume2)
    }

    fun testOrder(): List<Order> {

        val order1 = Order(
                        id = UUID.randomUUID().toString(),
                        itemList = listOf(
                            ItemsToBePurchased(
                                product = testProduct()[0], //book
                                quantity = 1
                            ),
                            ItemsToBePurchased(
                                product = testProduct()[3], // musicCD
                                quantity = 1
                            ),
                            ItemsToBePurchased(
                                product = testProduct()[1], // chocolate bar
                                quantity = 1
                            )
                        )
                    )

        val order2 = Order (
                id = UUID.randomUUID().toString(),
                itemList = listOf(
                    ItemsToBePurchased(
                        product = testProduct()[5], // imported chocolate
                        quantity = 1
                    ),
                    ItemsToBePurchased(
                        product = testProduct()[6], // imported perfume
                        quantity = 1
                    )
                )
            )

        val order3 = Order(
                id = UUID.randomUUID().toString(),
                itemList = listOf(
                    ItemsToBePurchased(
                        product = testProduct()[8], // imported perfume 2
                        quantity = 1
                    ),
                    ItemsToBePurchased(
                        product = testProduct()[4], // perfume
                        quantity = 1
                    ),
                    ItemsToBePurchased(
                        product = testProduct()[2], // headache pills
                        quantity = 1
                    ),
                    ItemsToBePurchased(
                        product = testProduct()[7], // imported box of chocs
                        quantity = 1
                    )
                )
            )

        return listOf(order1, order2, order3)
    }

    fun testReceipt(): List<Receipt> {

        val purchased = Purchased (
            product = testProduct()[3], //music CD
            tax = 0.10,
            finalPrice = 16.49,
            quantity = 1,
            salesTax = 1.50
        )

        val receipt1 = Receipt(
            id = UUID.randomUUID().toString(),
            purchasedList = listOf(purchased),
            totalPrice = 16.49,
            totalTax = 0.10,
            orderId = "1223"
        )

        return listOf(receipt1)
    }
}