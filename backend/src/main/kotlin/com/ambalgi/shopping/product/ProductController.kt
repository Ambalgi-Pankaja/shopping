package com.ambalgi.shopping.product

import com.ambalgi.shopping.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("products")
class ProductController(
    private val productService: ProductService
) {
    @PostMapping
    fun post(
        @RequestBody product: Product
    ): Product = productService.save(product)

    @GetMapping("/{id}")
    fun findById(
        @PathVariable("id") id: String
    ): Product? = productService.findById(id)

    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable("id") id: String
    ) = productService.deleteById(id)

    @PutMapping("/{id}")
    fun put(
        @PathVariable("id") id: String,
        @RequestBody product: Product
    ): Product? = productService.updateById(id, product)

    @GetMapping
    fun findAll(pageable: Pageable): Page<Product> = productService.findAll(pageable = pageable)

    @GetMapping(params = ["productName"])
    fun findByName(
        @RequestParam("productName") productName: String
    ): Product? = productService.findByProductName(productName)
}