package com.ambalgi.shopping.receiptTest

import com.ambalgi.shopping.Purchased
import com.ambalgi.shopping.TestBase
import com.ambalgi.shopping.order.OrderService
import com.ambalgi.shopping.receipt.ReceiptService
import com.ambalgi.shopping.util.UtilFunctions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertEquals

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReceiptServiceTest: TestBase(){

    @Autowired
    lateinit var receiptService: ReceiptService

    @Autowired
    lateinit var utilFunctions: UtilFunctions

    @Test
    fun `test to get total tax and total price for Input 1`() {

        val purchased1 = Purchased (
            product = testProduct()[0], //book
            quantity = 1,
            tax = OrderService.categoryTax,
            finalPrice = testProduct()[0].price,
            salesTax = 0.0
        )

        val purchased2 = Purchased (
            product = testProduct()[3], // music CD
            quantity = 1,
            tax = OrderService.salesTax,
            finalPrice = 16.49,
            salesTax = 1.50
        )

        val purchased3 = Purchased (
            product = testProduct()[1], // chocolate bar
            quantity = 1,
            tax = OrderService.categoryTax,
            finalPrice = testProduct()[1].price,
            salesTax = 0.0
        )

        val receipt = receiptService.getTotalPriceAndTax(
            listOf(purchased1, purchased2, purchased3)
        )

        assertEquals(receipt.second, 1.50, "Quantity of the product should be equal")
        assertEquals(receipt.first, 29.83, "Price should be equal after applying tax")
    }

    @Test
    fun `test to get total tax and total price for Input 2`() {

        val purchased1 = Purchased (
            product = testProduct()[5], //imported box of chocolates
            quantity = 1,
            tax = OrderService.categoryTax + OrderService.importTax,
            finalPrice = 10.50,
            salesTax = utilFunctions.customRound(0.05 * testProduct()[5].price)
        )

        val purchased2 = Purchased (
            product = testProduct()[6], // imported perfume
            quantity = 1,
            tax = OrderService.salesTax + OrderService.importTax,
            finalPrice = 54.65,
            salesTax = utilFunctions.customRound(0.15 * testProduct()[6].price)
        )

        val receipt = receiptService.getTotalPriceAndTax(
            listOf(purchased1, purchased2)
        )

        assertEquals(receipt.second, 7.65, "Sales tax should be equal")
        assertEquals(receipt.first, 65.15, "Price should be equal after applying tax")
    }

    @Test
    fun `test to get total tax and total price for Input 3`() {

        val purchased1 = Purchased (
            product = testProduct()[8], //imported bottle of perfume
            quantity = 1,
            tax = OrderService.salesTax + OrderService.importTax,
            finalPrice = 32.19,
            salesTax = utilFunctions.customRound(0.15 * testProduct()[8].price)
        )

        val purchased2 = Purchased (
            product = testProduct()[4], // bottle perfume
            quantity = 1,
            tax = OrderService.salesTax,
            finalPrice = 20.89,
            salesTax = utilFunctions.customRound(0.10 * testProduct()[4].price)
        )
        val purchased3 = Purchased (
            product = testProduct()[2], // headache pills
            quantity = 1,
            tax = OrderService.categoryTax,
            finalPrice = 9.75,
            salesTax = 0.00 * testProduct()[2].price
        )
        val purchased4 = Purchased (
            product = testProduct()[7], // imported box of choc
            quantity = 1,
            tax = OrderService.importTax + OrderService.categoryTax,
            finalPrice = 11.85,
            salesTax = utilFunctions.customRound(0.05 * testProduct()[7].price)
        )

        val receipt = receiptService.getTotalPriceAndTax(
            listOf(purchased1, purchased2, purchased3, purchased4)
        )

        assertEquals(receipt.second, 6.65, "Sales tax should be equal")
        assertEquals(receipt.first, 74.68, "Price should be equal after applying tax")
    }

    @Test
    fun `test generate receipt for order 1`(){
        orderRepository.deleteAll()

        val order = orderRepository.save(testOrder()[0])

        val receipt1 = receiptService.generateReceipt(order.id)

        assertEquals(receipt1.totalTax, 1.50, "Sales tax should match")
        assertEquals(receipt1.totalPrice, 29.83, "Total price should match")
    }

    @Test
    fun `test generate receipt for order 2`(){
        orderRepository.deleteAll()

        val order = orderRepository.save(testOrder()[1])

        val receipt2 = receiptService.generateReceipt(order.id)

        assertEquals(receipt2.totalTax, 7.65, "Sales tax should match")
        assertEquals(receipt2.totalPrice, 65.15, "Total price should match")
    }

    @Test
    fun `test generate receipt for order 3`(){
        orderRepository.deleteAll()

        val order = orderRepository.save(testOrder()[2])

        val receipt3 = receiptService.generateReceipt(order.id)

        assertEquals(receipt3.totalTax, 6.65, "Sales tax should match")
        assertEquals(receipt3.totalPrice, 74.63, "Total price should match")
    }
}