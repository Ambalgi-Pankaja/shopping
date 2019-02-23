package com.ambalgi.shopping.productTest

import com.ambalgi.shopping.TestBase
import com.ambalgi.shopping.product.ProductService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertEquals

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ProductServiceTest: TestBase() {

    @Autowired
    lateinit var productService: ProductService

    @Test
    fun `test if update is updating the existing product`(){
        productRepository.deleteAll()
        val existingProduct = productRepository.save(testProduct()[0])

        val updatedProduct = productService.updateById(
            existingProduct.id ?:"", existingProduct.copy(productName = "UpdatedName")
        )

        assertEquals(existingProduct.id, updatedProduct?.id)
        assertEquals("UpdatedName", updatedProduct?.productName)
    }
}