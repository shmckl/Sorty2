package com.example.sorty2.data.models

import java.util.Date

data class Bill(
    val id: String,
    val description: String,
    val amount: Float,
    val payer: String,
    val payees: List<String>,
    val shares: List<Float>,
    val date: Date
)
