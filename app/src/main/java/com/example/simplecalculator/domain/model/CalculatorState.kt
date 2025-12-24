package com.example.simplecalculator.domain.model

import java.math.BigDecimal

/**
 * Состояние калькулятора для хранения операндов и результата
 */
data class CalculatorState(
    /** Первое введенное число (для выполнения операции с ним) */
    val firstOperand: BigDecimal = BigDecimal.ZERO,

    /** Второе введенное число (запоминается для повторных нажатий "=") */
    val secondOperand: BigDecimal = BigDecimal.ZERO,

    /** Текущая выбранная операция (+, -, *, /) */
    val operation: Operation? = null,

    /** Результат последнего вычисления */
    val result: BigDecimal = BigDecimal.ZERO,

    /** Текст, отображаемый на дисплее в данный момент (накапливаемый ввод) */
    val displayText: String = "0",

    /** Флаг, указывающий, нужно ли начать ввод нового числа при нажатии цифры.
     * True после выбора операции или нажатия "=" */
    val isNewInput: Boolean = true,

    /** Флаг наличия десятичной точки в текущем числе, чтобы не добавить её дважды */
    val hasDecimalPoint: Boolean = false
)
