package com.ambalgi.shopping.product

import com.ambalgi.shopping.Product
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: PagingAndSortingRepository<Product, String>{

    fun findByProductName(name: String): Product?
}