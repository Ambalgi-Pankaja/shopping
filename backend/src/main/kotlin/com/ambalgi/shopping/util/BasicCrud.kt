package com.ambalgi.shopping.util

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BasicCrud<K, T> {
    fun findAll(pageable: Pageable): Page<T>

    fun findById(id: K): T?

    fun insert(obj: T): T

    fun deleteById(id: K)

    fun updateById(id: K, obj: T): T?
}