package com.example.simplecalculator.presentation

/**
 * UI State для экрана калькулятора
 */
data class CalculatorUiState(
    val displayValue: String = "0",
    val expression: String = "",
    val isError: Boolean = false
)
