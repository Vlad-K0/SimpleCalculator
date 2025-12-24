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
     * Выполняет основное вычисление (сложение, вычитание, умножение, деление).
     * Берет два числа и операцию из переданного состояния.
     * Возвращает результат типа BigDecimal для высокой точности.
     * При попытке деления на ноль выбрасывает ArithmeticException.
     */
    fun calculate(state: CalculatorState): BigDecimal {
        val first = state.firstOperand
        val second = state.secondOperand
        val operation = state.operation ?: return first

        return try {
            //в зависимости от операции выполняется вычисление
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
     * Вычисляет значение процента в зависимости от контекста операции.
     * Реализует логику стандартного калькулятора:
     * - "Число + 10%" -> Число + (10% от Числа)
     * - "Число * 10%" -> Число * 0.1
     * Если операции нет, просто превращает проценты в десятичную дробь (50% -> 0.5).
     */
    fun calculatePercentage(
        base: BigDecimal,
        percent: BigDecimal,
        operation: Operation?
    ): BigDecimal {
        val percentValue = percent.divide(BigDecimal(100), MATH_CONTEXT)
        
        return when (operation) {
            Operation.ADD, Operation.SUBTRACT -> {
                // Для + и - вычисляем реальное значение процента от базового числа
                base.multiply(percentValue, MATH_CONTEXT)
            }
            Operation.MULTIPLY, Operation.DIVIDE -> {
                // Для умножения и деления просто используем коэффициент (например 0.1 для 10%)
                percentValue
            }
            null -> {
                // Если нет операции, возвращаем просто долю
                percentValue
            }
        }
    }

    /**
     * Форматирует число BigDecimal в строку для красивого отображения на дисплее.
     * - Удаляет лишние нули в конце (например, 12.500 -> 12.5).
     * - Если число слишком длинное (> 15 цифр), конвертирует в научный формат (например, 1.23E+10).
     * - Если число целое, убирает десятичную точку.
     */
    fun formatResult(value: BigDecimal): String {
        // Убираем лишние нули
        val stripped = value.stripTrailingZeros()
        
        // Проверяем, помещается ли число в дисплей обычной строкой
        val plainString = stripped.toPlainString()
        
        return if (plainString.length > MAX_DISPLAY_DIGITS) {
            // Если слишком длинное, используем инженерную нотацию с округлением
            val formatted = stripped.round(MathContext(MAX_DISPLAY_DIGITS - 5))
            formatted.toEngineeringString()
        } else {
            // Если помещается, проверяем нужно ли показывать точку
            if (stripped.scale() <= 0) {
                stripped.toBigInteger().toString()
            } else {
                plainString
            }
        }
    }

    /**
     * Преобразует (парсит) строку с экрана обратно в число BigDecimal для вычислений.
     * Обрабатывает пустые строки или одиночные знаки минуса как 0.
     * Используется перед выполнением математических операций.
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
