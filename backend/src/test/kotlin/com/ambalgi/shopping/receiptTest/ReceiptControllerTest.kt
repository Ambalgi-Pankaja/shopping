package com.ambalgi.shopping.receiptTest

import com.ambalgi.shopping.TestBase
import com.ambalgi.shopping.receipt.ReceiptService
import com.ambalgi.shopping.util.UtilFunctions
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReceiptControllerTest: TestBase(){

    @Autowired
    lateinit var receiptService: ReceiptService

    @Test
    fun `test to find receipt By Id`(){
        receiptRepository.deleteAll()
        val request = receiptRepository.save(testReceipt()[0])

        RestAssured.given()
            .get("/receipts/${request.id}}")
            .then()
            .statusCode(200)
    }

    @Test
    fun `test to find receipt By Order Id`(){
        receiptRepository.deleteAll()
        val request = receiptRepository.save(testReceipt()[0])

        RestAssured.given()
            .get("/receipts/${request.id}}")
            .then()
            .statusCode(200)
    }

    @Test
    fun `test to find receipt Order 1`(){
        orderRepository.deleteAll()

        val order = orderRepository.save(testOrder()[0])

        RestAssured.given()
            .get("/receipts/order/${order.id}")
            .then()
            .statusCode(200)
            .body("orderId", equalTo(order.id))
    }
}