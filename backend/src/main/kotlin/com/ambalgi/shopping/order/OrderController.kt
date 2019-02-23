package com.ambalgi.shopping.order

import com.ambalgi.shopping.Order
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("orders")
class OrderController(
    private val orderService: OrderService
) {

    @PostMapping
    fun post(
        @RequestBody order: Order
    ): Order = orderService.insert(order)

    @GetMapping("/{id}")
    fun findById(
        @PathVariable("id") id: String
    ): Order? = orderService.findById(id)

    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable("id") id: String
    ) = orderService.deleteById(id)

    @PutMapping("/{id}")
    fun put(
        @PathVariable("id") id: String,
        @RequestBody order: Order
    ): Order? = orderService.updateById(id, order)

    @GetMapping
    fun findAll(pageable: Pageable): Page<Order> = orderService.findAll(pageable = pageable)

}