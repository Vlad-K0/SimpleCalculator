package com.example.simplecalculator.domain.model

import java.math.BigDecimal

/**
 * Состояние калькулятора для хранения операндов и результата
 */
data class CalculatorState(
    val firstOperand: BigDecimal = BigDecimal.ZERO,
    val secondOperand: BigDecimal = BigDecimal.ZERO,
    val operation: Operation? = null,
    val result: BigDecimal = BigDecimal.ZERO,
    val displayText: String = "0",
    val isNewInput: Boolean = true,
    val hasDecimalPoint: Boolean = false
)
