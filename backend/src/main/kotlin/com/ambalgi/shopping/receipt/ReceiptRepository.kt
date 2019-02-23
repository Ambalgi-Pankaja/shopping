package com.ambalgi.shopping.receipt

import com.ambalgi.shopping.Receipt
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ReceiptRepository: MongoRepository<Receipt, String>