package com.example.nearbyeats_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.nearbyeats_app.domain.usecase.CalculateTipUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CalculatorViewModel : ViewModel() {

    private val calculateTipUseCase = CalculateTipUseCase()

    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount

    private val _tipPercent = MutableStateFlow("10")
    val tipPercent: StateFlow<String> = _tipPercent

    private val _people = MutableStateFlow("1")
    val people: StateFlow<String> = _people

    private val _tipAmount = MutableStateFlow(0.0)
    val tipAmount: StateFlow<Double> = _tipAmount

    private val _totalWithTip = MutableStateFlow(0.0)
    val totalWithTip: StateFlow<Double> = _totalWithTip

    private val _perPerson = MutableStateFlow(0.0)
    val perPerson: StateFlow<Double> = _perPerson

    fun onAmountChanged(value: String) {
        _amount.value = value
        recalculate()
    }

    fun onTipPercentChanged(value: String) {
        _tipPercent.value = value
        recalculate()
    }

    fun onPeopleChanged(value: String) {
        _people.value = value
        recalculate()
    }

    private fun recalculate() {
        val amt = _amount.value.toDoubleOrNull() ?: 0.0
        val tip = _tipPercent.value.toDoubleOrNull() ?: 0.0
        val ppl = _people.value.toIntOrNull()?.coerceAtLeast(1) ?: 1

        _tipAmount.value = calculateTipUseCase.calculateTip(amt, tip)
        _totalWithTip.value = calculateTipUseCase.totalWithTip(amt, tip)
        _perPerson.value = calculateTipUseCase.splitBill(_totalWithTip.value, ppl)
    }
}
