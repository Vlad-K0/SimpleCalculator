package com.example.simplecalculator.domain.model

/**
 * Математические операции калькулятора
 */
enum class Operation(val symbol: String) {
    ADD("+"),
    SUBTRACT("−"),
    MULTIPLY("×"),
    DIVIDE("÷")
}
