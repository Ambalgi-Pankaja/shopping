package com.ambalgi.shopping.product

import com.ambalgi.shopping.Product
import com.ambalgi.shopping.util.BasicCrud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
): BasicCrud<String, Product>{

    override fun findAll(pageable: Pageable): Page<Product> = productRepository.findAll(pageable)

    override fun insert(obj: Product): Product = productRepository.save(obj)

    fun save(product: Product): Product = productRepository
        .save(product)

    override fun findById(id: String): Product? = productRepository
            .findById(id)
            .orElse(null)

    override fun deleteById(id: String) = productRepository
        .deleteById(id)

    fun findByProductName(name: String): Product? = productRepository
        .findByProductName(name)

    override fun updateById(id: String, obj: Product): Product? {
        val existingProduct = productRepository
                .findById(id).orElse(null)

        return if (existingProduct == null)
                    error("Product not found")
               else productRepository.save(
                    obj.copy(id = existingProduct.id)
            )
    }
}