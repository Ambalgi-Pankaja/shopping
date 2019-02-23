package com.ambalgi.shopping.order

import com.ambalgi.shopping.Category
import com.ambalgi.shopping.ItemsToBePurchased
import com.ambalgi.shopping.Order
import com.ambalgi.shopping.Product
import com.ambalgi.shopping.Purchased
import com.ambalgi.shopping.util.BasicCrud
import com.ambalgi.shopping.util.UtilFunctions
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val utilFunctions: UtilFunctions
): BasicCrud<String, Order> {

    companion object {
        const val importTax = 0.05
        const val salesTax = 0.10
        const val categoryTax = 0.0
    }

    override fun updateById(id: String, obj: Order): Order? {
        val existingOrder = orderRepository
            .findById(id).orElse(null)

        return if (existingOrder == null)
            error("Order not found")
        else orderRepository.save(
            obj.copy(id = existingOrder.id)
        )
    }

    override fun findAll(pageable: Pageable): Page<Order> = orderRepository.findAll(pageable)

    override fun findById(id: String): Order? = orderRepository.findById(id).orElse(null)

    override fun insert(obj: Order): Order = orderRepository.insert(obj)

    override fun deleteById(id: String) = orderRepository.deleteById(id)

    fun buildPurchased(orderId: String): List<Purchased>{

        val order = findById(orderId) ?: error("Order with id $orderId Not found")

        val purchasedList = mutableListOf<Purchased>()

        order.itemList.forEach {
            val purchased = applyTax(it)
            purchasedList.add(purchased)
        }
        return purchasedList
    }

    fun applyTax(itemsToBePurchased: ItemsToBePurchased): Purchased {

        val tax = calculateTax(itemsToBePurchased.product)

        val price = itemsToBePurchased.product.price

        val finalPrice = if (tax == 0.0)
                                    price
                                else
                                    String.format("%.2f",utilFunctions.customRound(price*tax) + price).toDouble()

        return Purchased(
            product = itemsToBePurchased.product,
            tax = tax,
            salesTax = utilFunctions.customRound(itemsToBePurchased.product.price*tax),
            quantity = itemsToBePurchased.quantity,
            finalPrice = finalPrice
        )
    }

    fun calculateTax(product: Product): Double {

        var tax = if (product.category is Category) categoryTax
                         else salesTax

        tax = if(product.imported) tax + importTax
              else tax

        return String.format("%.2f", tax).toDouble()
    }
}