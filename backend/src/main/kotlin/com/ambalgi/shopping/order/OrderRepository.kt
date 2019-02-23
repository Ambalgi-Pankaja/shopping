package com.ambalgi.shopping.order

import com.ambalgi.shopping.Order
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: MongoRepository<Order, String>