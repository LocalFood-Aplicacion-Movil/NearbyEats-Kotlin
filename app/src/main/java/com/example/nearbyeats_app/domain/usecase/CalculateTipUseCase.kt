package com.example.nearbyeats_app.domain.usecase

class CalculateTipUseCase {
    fun calculateTip(amount: Double, tipPercent: Double): Double =
        amount * (tipPercent / 100.0)

    fun totalWithTip(amount: Double, tipPercent: Double): Double =
        amount + calculateTip(amount, tipPercent)

    fun splitBill(total: Double, people: Int): Double =
        if (people > 0) total / people else 0.0
}
