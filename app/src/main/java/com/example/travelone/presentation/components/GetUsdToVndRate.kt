package com.example.travelone.presentation.components

import java.text.NumberFormat
import java.util.Locale

fun getUsdToVndRate(): Double {
    return 26231.0
}

fun formatPrice(priceUSD: Int): String {
    val languageCode = Locale.getDefault().language
    return if (languageCode == "vi") {
        val rate = getUsdToVndRate()
        val priceVND = priceUSD * rate
        val formatter = NumberFormat.getNumberInstance(Locale("vi", "VN"))
        "${formatter.format(priceVND.toLong())} đ"
    } else {
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        "$${formatter.format(priceUSD)}"
    }
}