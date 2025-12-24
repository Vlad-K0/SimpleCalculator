package com.example.simplecalculator.presentation

import androidx.lifecycle.ViewModel
import com.example.simplecalculator.domain.model.CalculatorState
import com.example.simplecalculator.domain.model.Operation
import com.example.simplecalculator.domain.usecase.CalculatorUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal

/**
 * ViewModel для калькулятора с обработкой всех событий
 */
class CalculatorViewModel(
    private val calculatorUseCase: CalculatorUseCase = CalculatorUseCase()
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    private var calculatorState = CalculatorState()

    /**
     * Обработка нажатия на цифру
     */
    fun onNumberClick(number: String) {
        if (_uiState.value.isError) {
            onClearClick()
        }

        val currentDisplay = calculatorState.displayText

        val newDisplay = when {
            calculatorState.isNewInput -> number
            currentDisplay == "0" && number == "0" -> "0"
            currentDisplay == "0" -> number
            currentDisplay.replace("-", "").replace(".", "").length >= 15 -> currentDisplay
            else -> currentDisplay + number
        }

        calculatorState = calculatorState.copy(
            displayText = newDisplay,
            isNewInput = false
        )

        updateUiState()
    }

    /**
     * Обработка нажатия на операцию (+, -, ×, ÷)
     */
    fun onOperationClick(operation: Operation) {
        if (_uiState.value.isError) {
            onClearClick()
            return
        }

        // Если уже есть операция и введён второй операнд - вычисляем
        if (calculatorState.operation != null && !calculatorState.isNewInput) {
            calculateResult()
        }

        val currentValue = calculatorUseCase.parseInput(calculatorState.displayText)

        calculatorState = calculatorState.copy(
            firstOperand = currentValue,
            operation = operation,
            isNewInput = true
        )

        updateUiState()
    }

    /**
     * Обработка нажатия на кнопку "="
     */
    fun onEqualsClick() {
        if (_uiState.value.isError || calculatorState.operation == null) {
            return
        }

        calculateResult()
    }

    /**
     * Выполнение вычисления
     */
    private fun calculateResult() {
        try {
            val secondOperand = calculatorUseCase.parseInput(calculatorState.displayText)

            calculatorState = calculatorState.copy(secondOperand = secondOperand)

            val result = calculatorUseCase.calculate(calculatorState)
            val formattedResult = calculatorUseCase.formatResult(result)

            calculatorState = CalculatorState(
                firstOperand = result,
                displayText = formattedResult,
                result = result,
                isNewInput = true
            )

            _uiState.update {
                it.copy(
                    displayValue = formattedResult,
                    expression = "",
                    isError = false
                )
            }
        } catch (e: ArithmeticException) {
            _uiState.update {
                it.copy(
                    displayValue = "Error",
                    isError = true
                )
            }
        }
    }

    /**
     * Очистка калькулятора (AC)
     */
    fun onClearClick() {
        calculatorState = CalculatorState()
        _uiState.update {
            CalculatorUiState()
        }
    }

    /**
     * Удаление последнего символа (backspace)
     */
    fun onDeleteClick() {
        if (_uiState.value.isError) {
            onClearClick()
            return
        }

        val currentDisplay = calculatorState.displayText

        val newDisplay = when {
            currentDisplay.length == 1 -> "0"
            currentDisplay.length == 2 && currentDisplay.startsWith("-") -> "0"
            else -> currentDisplay.dropLast(1)
        }

        calculatorState = calculatorState.copy(
            displayText = newDisplay,
            hasDecimalPoint = newDisplay.contains(".")
        )

        updateUiState()
    }

    /**
     * Добавление десятичной точки
     */
    fun onDecimalClick() {
        if (_uiState.value.isError) {
            onClearClick()
        }

        if (calculatorState.displayText.contains(".")) {
            return
        }

        val newDisplay = if (calculatorState.isNewInput) {
            "0."
        } else {
            calculatorState.displayText + "."
        }

        calculatorState = calculatorState.copy(
            displayText = newDisplay,
            isNewInput = false,
            hasDecimalPoint = true
        )

        updateUiState()
    }

    /**
     * Вычисление процента (логика как в Android калькуляторе)
     */
    fun onPercentClick() {
        if (_uiState.value.isError) {
            onClearClick()
            return
        }

        val currentValue = calculatorUseCase.parseInput(calculatorState.displayText)

        val percentResult = if (calculatorState.operation != null) {
            // Есть операция - вычисляем процент от первого операнда
            calculatorUseCase.calculatePercentage(
                calculatorState.firstOperand,
                currentValue,
                calculatorState.operation
            )
        } else {
            // Нет операции - просто переводим в десятичную дробь
            calculatorUseCase.calculatePercentage(
                BigDecimal.ZERO,
                currentValue,
                null
            )
        }

        val formattedResult = calculatorUseCase.formatResult(percentResult)

        calculatorState = calculatorState.copy(
            displayText = formattedResult,
            isNewInput = false
        )

        updateUiState()
    }

    /**
     * Смена знака числа (+/-)
     */
    fun onNegateClick() {
        if (_uiState.value.isError) {
            onClearClick()
            return
        }

        val currentDisplay = calculatorState.displayText
        
        val newDisplay = when {
            currentDisplay == "0" -> "0"
            currentDisplay.startsWith("-") -> currentDisplay.drop(1)
            else -> "-$currentDisplay"
        }

        calculatorState = calculatorState.copy(
            displayText = newDisplay,
            isNewInput = false
        )
        updateUiState()
    }

    /**
     * Обновление UI State
     */
    private fun updateUiState() {
        // Логика отображения: показывать "5 + 3" в одной строке
        val displayValue = if (calculatorState.operation != null) {
            val first = calculatorUseCase.formatResult(calculatorState.firstOperand)
            val op = calculatorState.operation!!.symbol
            
            if (calculatorState.isNewInput) {
                // Ждем ввода второго числа: "5 + "
                "$first $op"
            } else {
                // Ввод второго числа: "5 + 3"
                val second = calculatorState.displayText
                "$first $op $second"
            }
        } else {
            // Нет операции (начало или результат): "5" или "Error"
            calculatorState.displayText
        }

        _uiState.update {
            it.copy(
                displayValue = displayValue,
                expression = "" // Верхнее поле не используется
            )
        }
    }
}
