package com.ambalgi.shopping.productTest

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
class ProductControllerTest: TestBase() {

    @Test
    fun `test to find all products`(){
        RestAssured.given()
            .get("/products")
            .then()
            .statusCode(200)
    }

    @Test
    fun `test to find a product by productId`(){
        productRepository.deleteAll()
        val request = productRepository.save(testProduct()[0])

        RestAssured.given()
            .get("/products/${request.id}}")
            .then()
            .statusCode(200)
    }

    @Test
    fun `test to delete a product`() {
        productRepository.deleteAll()
        val request = productRepository.save(testProduct()[0])

        RestAssured.given()
            .delete("/products/${request.id}")
            .then()
            .statusCode(200)
    }

    @Test
    fun `test to post a product`(){
        val request = testProduct()[0]

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/products")
            .then()
            .statusCode(200)
            .assertThat()
            .body("productName", Matchers.equalTo(request.productName))
    }

    @Test
    fun `test to update a product by productId`(){
        productRepository.deleteAll()
        val product = productRepository.save(testProduct()[0])

        val request = product.copy(productName = "UpdatedName")

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(request)
            .put("/products/${product.id}")
            .then()
            .statusCode(200)
            .assertThat()
            .body("productName", Matchers.equalTo(request.productName))
    }

    @Test
    fun `test to find a product by productName`(){
        productRepository.deleteAll()
        val product = productRepository.save(testProduct()[0])

        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .queryParam("productName", product.productName)
            .`when`()
            .get("/products")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .assertThat()
            .body("productName", Matchers.equalTo("book"))
    }
}