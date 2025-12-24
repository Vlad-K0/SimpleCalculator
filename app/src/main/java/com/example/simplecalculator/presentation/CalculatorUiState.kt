package com.example.simplecalculator.presentation

/**
 * UI State для экрана калькулятора
 */
data class CalculatorUiState(
    /** Основное значение, отображаемое на экране (крупным шрифтом) */
    val displayValue: String = "0",

    /** Строка выражения (например, "5 + 3"). Сейчас не используется в UI, так как объединено с main display. */
    val expression: String = "",

    /** Флаг ошибки (например, при делении на ноль). Блокирует ввод до очистки. */
    val isError: Boolean = false
)
