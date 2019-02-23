package com.ambalgi.shopping.receipt

import com.ambalgi.shopping.Purchased
import com.ambalgi.shopping.Receipt
import com.ambalgi.shopping.order.OrderService
import com.ambalgi.shopping.product.ProductService
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import org.springframework.stereotype.Service
import java.io.FileWriter
import java.io.IOException

@Service
class ReceiptService(
    private val receiptRepository: ReceiptRepository,
    private val productService: ProductService,
    private val orderService: OrderService
) {

    fun findAll(): List<Receipt?> = receiptRepository.findAll()

    fun findById(id: String): Receipt? = receiptRepository.findById(id).orElse(null)

    fun generateReceipt(orderId: String): Receipt {

        val purchasedList = orderService.buildPurchased(orderId)

        val (totalPriceIncludingTax, totalSalesTax) = getTotalPriceAndTax(purchasedList)

        val receipt = Receipt(
                orderId = orderId,
                purchasedList = purchasedList,
                totalTax = totalSalesTax,
                totalPrice = totalPriceIncludingTax
            )

        printReceipt(receipt)

        return receiptRepository.save(receipt)
    }

    fun getTotalPriceAndTax(purchasedList: List<Purchased>): Pair<Double, Double> {
        var totalPriceIncludingTax = String.format("%.2f", 0.0).toDouble()
        var totalSalesTax = String.format("%.2f", 0.0).toDouble()

        for(item in purchasedList) {
            totalPriceIncludingTax += item.finalPrice
            totalSalesTax += item.salesTax
        }

        return Pair(
            String.format("%.2f", totalPriceIncludingTax).toDouble(),
            String.format("%.2f", totalSalesTax).toDouble()
        )
    }

    private fun printReceipt(receipt: Receipt) {
        val fileWriter = FileWriter("Receipt_${receipt.orderId}.csv")


        fileWriter.append("INPUT product list \n")
        fileWriter.append("----------------------------------------\n")
        receipt.purchasedList.forEach{
            fileWriter.append(it.quantity.toString() + " ")
            fileWriter.append(it.product.productName + " at ")
            fileWriter.append((it.product.price*it.quantity).toString() + "\n")
        }
        fileWriter.append("\n")
        fileWriter.append("----------------------------------------\n")
        fileWriter.append("OUTPUT \n")
        fileWriter.append("----------------------------------------\n")
        try {
            receipt.run {
                this.purchasedList.map {
                    fileWriter.append(it.quantity.toString() + " ")
                    fileWriter.append(it.product.productName + ": ")
                    fileWriter.append(it.finalPrice.toString() + " \n")
                }
                fileWriter.append("Sales Taxes: ${this.totalTax}" + " \n")
                fileWriter.append("Total: ${this.totalPrice}" + " \n")
            }
        }catch (e: Exception) {
            log.println("Writing CSV error")
        } finally {
            try {
                fileWriter.close()
            } catch (e: IOException) {
                log.println("Flushing/closing error!")
                e.printStackTrace()
            }
        }
    }
 }