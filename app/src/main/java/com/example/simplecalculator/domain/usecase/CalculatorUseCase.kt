package com.example.simplecalculator.domain.usecase

import com.example.simplecalculator.domain.model.CalculatorState
import com.example.simplecalculator.domain.model.Operation
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

/**
 * Use case для вычислений калькулятора.
 * Использует BigDecimal для точности с большими и маленькими числами.
 */
class CalculatorUseCase {

    companion object {
        private val MATH_CONTEXT = MathContext.DECIMAL128
        private const val MAX_DISPLAY_DIGITS = 15
        private const val DECIMAL_SCALE = 10
    }

    /**
     * Выполняет вычисление на основе текущего состояния
     */
    fun calculate(state: CalculatorState): BigDecimal {
        val first = state.firstOperand
        val second = state.secondOperand
        val operation = state.operation ?: return first

        return try {
            when (operation) {
                Operation.ADD -> first.add(second, MATH_CONTEXT)
                Operation.SUBTRACT -> first.subtract(second, MATH_CONTEXT)
                Operation.MULTIPLY -> first.multiply(second, MATH_CONTEXT)
                Operation.DIVIDE -> {
                    if (second.compareTo(BigDecimal.ZERO) == 0) {
                        throw ArithmeticException("Division by zero")
                    }
                    first.divide(second, DECIMAL_SCALE, RoundingMode.HALF_UP)
                }
            }
        } catch (e: ArithmeticException) {
            throw e
        }
    }

    /**
     * Вычисляет процент как в стандартном Android калькуляторе:
     * - 100 + 10% = 100 + (100 × 0.10) = 110
     * - 100 - 10% = 100 - (100 × 0.10) = 90  
     * - 100 × 10% = 100 × 0.10 = 10
     * - 100 ÷ 10% = 100 ÷ 0.10 = 1000
     * - 50% (без операции) = 0.50
     */
    fun calculatePercentage(
        base: BigDecimal,
        percent: BigDecimal,
        operation: Operation?
    ): BigDecimal {
        val percentValue = percent.divide(BigDecimal(100), MATH_CONTEXT)
        
        return when (operation) {
            Operation.ADD, Operation.SUBTRACT -> {
                // Для + и - процент вычисляется от базы
                base.multiply(percentValue, MATH_CONTEXT)
            }
            Operation.MULTIPLY, Operation.DIVIDE -> {
                // Для × и ÷ просто переводим в десятичную дробь
                percentValue
            }
            null -> {
                // Без операции - просто переводим в десятичную дробь
                percentValue
            }
        }
    }

    /**
     * Форматирует результат для отображения
     */
    fun formatResult(value: BigDecimal): String {
        // Убираем лишние нули
        val stripped = value.stripTrailingZeros()
        
        // Проверяем, помещается ли число в дисплей
        val plainString = stripped.toPlainString()
        
        return if (plainString.length > MAX_DISPLAY_DIGITS) {
            // Используем экспоненциальную нотацию для очень больших/маленьких чисел
            val formatted = stripped.round(MathContext(MAX_DISPLAY_DIGITS - 5))
            formatted.toEngineeringString()
        } else {
            // Проверяем на целое число
            if (stripped.scale() <= 0) {
                stripped.toBigInteger().toString()
            } else {
                plainString
            }
        }
    }

    /**
     * Парсит строку в BigDecimal
     */
    fun parseInput(input: String): BigDecimal {
        return try {
            if (input.isEmpty() || input == "-") {
                BigDecimal.ZERO
            } else {
                BigDecimal(input)
            }
        } catch (e: NumberFormatException) {
            BigDecimal.ZERO
        }
    }
}
