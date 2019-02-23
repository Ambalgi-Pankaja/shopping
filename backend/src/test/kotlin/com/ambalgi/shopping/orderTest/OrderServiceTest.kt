package com.ambalgi.shopping.orderTest

import com.ambalgi.shopping.ItemsToBePurchased
import com.ambalgi.shopping.TestBase
import com.ambalgi.shopping.order.OrderService
import com.ambalgi.shopping.product.ProductService
import com.ambalgi.shopping.util.UtilFunctions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertEquals

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class OrderServiceTest: TestBase(){

    @Autowired
    lateinit var orderService: OrderService

    @Autowired
    lateinit var productService: ProductService

    @Autowired
    lateinit var utilFunctions: UtilFunctions

    @Test
    fun `test tax of normal goods`() {
        productRepository.deleteAll()
        productRepository.saveAll(testProduct())

        val normalProduct = productService.findByProductName("music CD")
        ?: throw Exception ("Product not found")

        val medicinalProd = productService.findByProductName("headache Pills")
            ?: throw Exception ("Product not found")

        val importProduct = productService.findByProductName("imported perfume")
            ?: throw Exception ("Product not found")

        val importFood = productService.findByProductName("imported chocolate")
            ?: throw Exception ("Product not found")

        val normalTax= orderService.calculateTax(normalProduct)
        val categoryTax = orderService.calculateTax(medicinalProd)
        val importTax = orderService.calculateTax(importProduct)
        val importFoodTax = orderService.calculateTax(importFood)

        assertEquals(normalTax, 0.10, "Tax should be 10%")
        assertEquals(categoryTax, 0.0, "Tax should be 0%")
        assertEquals(importTax, 0.15, "Tax should be 15%")
        assertEquals(importFoodTax, 0.05, "Tax should be 5%")
    }

    @Test
    fun `test build purchase for products`() {

        val normalItem = ItemsToBePurchased (
            product = testProduct()[0], //book
            quantity = 1
        )

        val purchased = orderService.applyTax(normalItem)

        assertEquals(purchased.tax, 0.0, "No tax for category")
        assertEquals(purchased.finalPrice, 12.49, "Final and initial price should be same")
        assertEquals(purchased.salesTax, 0.0, "No Sales tax should be applied")
    }

    @Test
    fun `test build purchase for imported products`() {

        val normalItem = ItemsToBePurchased (
            product = testProduct()[6], // imported perfume
            quantity = 1
        )

        val purchased = orderService.applyTax(normalItem)

        assertEquals(purchased.tax, 0.15, "Import tax should be applied")
        assertEquals(purchased.finalPrice, 54.65, "Final should be added with tax")
    }

    @Test
    fun `test build purchase for imported food products`() {

        val importedFood = ItemsToBePurchased (
            product = testProduct()[5],
            quantity = 1
        )

        val purchased = orderService.applyTax(importedFood)

        assertEquals(purchased.tax, 0.05, "Import tax should be applied")
        assertEquals(purchased.finalPrice, 10.50, "Final should be added with tax")
    }

    @Test
    fun `test build purchase for imported normal products`() {

        val importedFood = ItemsToBePurchased (
            product = testProduct()[8],
            quantity = 1
        )

        val purchased = orderService.applyTax(importedFood)

        assertEquals(purchased.tax, 0.15, "Import tax should be applied")
        assertEquals(purchased.finalPrice, 32.19, "Final should be added with tax")
    }
}