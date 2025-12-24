package com.example.simplecalculator.domain.model

/**
 * Математические операции калькулятора
 */
enum class Operation(val symbol: String) {
    /** Сложение (+) */
    ADD("+"),
    
    /** Вычитание (−) */
    SUBTRACT("−"),
    
    /** Умножение (×) */
    MULTIPLY("×"),
    
    /** Деление (÷) */
    DIVIDE("÷")
}
