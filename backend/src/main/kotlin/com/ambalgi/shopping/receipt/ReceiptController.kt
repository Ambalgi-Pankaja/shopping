package com.ambalgi.shopping.receipt

import com.ambalgi.shopping.Receipt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("receipts")
class ReceiptController(
    private val receiptService: ReceiptService
) {
    @GetMapping
    fun findAll(): List<Receipt?> = receiptService.findAll()

    @GetMapping("/{id}")
    fun findById(
        @PathVariable("id") id: String
    ): Receipt? = receiptService.findById(id)

    @GetMapping("/order/{id}")
    fun getReceipt(
        @PathVariable("id") id: String
    ): Receipt = receiptService.generateReceipt(orderId = id)
}