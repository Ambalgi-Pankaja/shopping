package com.ambalgi.shopping.orderTest

import com.ambalgi.shopping.ItemsToBePurchased
import com.ambalgi.shopping.TestBase
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class OrderControllerTest: TestBase(){

    @Test
    fun `test to find all orders`(){
        RestAssured.given()
            .get("/orders")
            .then()
            .statusCode(200)
    }

    @Test
    fun `test to find a order by orderId`(){
        orderRepository.deleteAll()
        val request = orderRepository.save(testOrder()[0])

        RestAssured.given()
            .get("/orders/${request.id}}")
            .then()
            .statusCode(200)
    }

    @Test
    fun `test to delete an order`() {
        orderRepository.deleteAll()
        val request = orderRepository.save(testOrder()[0])

        RestAssured.given()
            .delete("/orders/${request.id}")
            .then()
            .statusCode(200)
    }

    @Test
    fun `test to post an order`(){
        val request = testOrder()[0]

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/orders")
            .then()
            .statusCode(200)
            .assertThat()
            .body("itemList[0].product.productName", Matchers.equalTo(request.itemList[0].product.productName))
    }

    @Test
    fun `test to update an order by orderId`(){
        orderRepository.deleteAll()

        val order = orderRepository.save(testOrder()[0])

        val request = order.copy(
            itemList = listOf(ItemsToBePurchased (product = testProduct()[0],
                quantity = 3
            )
        ))

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(request)
            .put("/orders/${order.id}")
            .then()
            .statusCode(200)
            .assertThat()
            .body("id", Matchers.equalTo(request.id))
    }
}