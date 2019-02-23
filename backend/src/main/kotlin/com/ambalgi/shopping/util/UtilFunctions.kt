package com.ambalgi.shopping.util

import org.springframework.stereotype.Service

@Service
class UtilFunctions {

    fun customRound(num: Double): Double {
        return Math.round(num * 20) / 20.0
    }
}