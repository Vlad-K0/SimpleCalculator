package com.example.simplecalculator.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simplecalculator.domain.model.Operation

/**
 * Композер клавиатуры калькулятора.
 * Располагает кнопки в сетке 5x4.
 * Использует [Column] и [Row] с весами (weight) для равномерного заполнения пространства.
 *
 * @param onNumberClick Коллбек нажатия на цифру.
 * @param onOperationClick Коллбек нажатия на операцию.
 * @param onEqualsClick Коллбек нажатия на "=".
 * @param onClearClick Коллбек нажатия на "AC".
 * @param onDeleteClick Коллбек для удаления (не используется в UI по умолчанию, но поддерживается VM).
 * @param onDecimalClick Коллбек нажатия на точку.
 * @param onPercentClick Коллбек нажатия на %.
 * @param onNegateClick Коллбек нажатия на +/-.
 */
@Composable
fun CalculatorKeypad(
    onNumberClick: (String) -> Unit,
    onOperationClick: (Operation) -> Unit,
    onEqualsClick: () -> Unit,
    onClearClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDecimalClick: () -> Unit,
    onPercentClick: () -> Unit,
    onNegateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val rowModifier = Modifier
            .weight(1f)
            .fillMaxWidth()

        // Первый ряд: AC, ±, %, ÷
        Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(
                symbol = "AC",
                type = CalculatorButtonType.FUNCTION,
                onClick = onClearClick,
                modifier = Modifier.weight(1f)
            )
            CalculatorButton(
                symbol = "±",
                type = CalculatorButtonType.FUNCTION,
                onClick = onNegateClick,
                modifier = Modifier.weight(1f)
            )
            CalculatorButton(
                symbol = "%",
                type = CalculatorButtonType.FUNCTION,
                onClick = onPercentClick,
                modifier = Modifier.weight(1f)
            )
            CalculatorButton(
                symbol = "÷",
                type = CalculatorButtonType.OPERATION,
                onClick = { onOperationClick(Operation.DIVIDE) },
                modifier = Modifier.weight(1f)
            )
        }

        // Второй ряд: 7, 8, 9, ×
        Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(
                symbol = "7",
                type = CalculatorButtonType.NUMBER,
                onClick = { onNumberClick("7") },
                modifier = Modifier.weight(1f)
            )
            CalculatorButton(
                symbol = "8",
                type = CalculatorButtonType.NUMBER,
                onClick = { onNumberClick("8") },
                modifier = Modifier.weight(1f)
            )
            CalculatorButton(
                symbol = "9",
                type = CalculatorButtonType.NUMBER,
                onClick = { onNumberClick("9") },
                modifier = Modifier.weight(1f)
            )
            CalculatorButton(
                symbol = "×",
                type = CalculatorButtonType.OPERATION,
                onClick = { onOperationClick(Operation.MULTIPLY) },
                modifier = Modifier.weight(1f)
            )
        }

        // Третий ряд: 4, 5, 6, −
        Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(
                symbol = "4",
                type = CalculatorButtonType.NUMBER,
                onClick = { onNumberClick("4") },
                modifier = Modifier.weight(1f)
            )
            CalculatorButton(
                symbol = "5",
                type = CalculatorButtonType.NUMBER,
                onClick = { onNumberClick("5") },
                modifier = Modifier.weight(1f)
            )
            CalculatorButton(
                symbol = "6",
                type = CalculatorButtonType.NUMBER,
                onClick = { onNumberClick("6") },
                modifier = Modifier.weight(1f)
            )
            CalculatorButton(
                symbol = "−",
                type = CalculatorButtonType.OPERATION,
                onClick = { onOperationClick(Operation.SUBTRACT) },
                modifier = Modifier.weight(1f)
            )
        }

        // Четвёртый ряд: 1, 2, 3, +
        Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(
                symbol = "1",
                type = CalculatorButtonType.NUMBER,
                onClick = { onNumberClick("1") },
                modifier = Modifier.weight(1f)
            )
            CalculatorButton(
                symbol = "2",
                type = CalculatorButtonType.NUMBER,
                onClick = { onNumberClick("2") },
                modifier = Modifier.weight(1f)
            )
            CalculatorButton(
                symbol = "3",
                type = CalculatorButtonType.NUMBER,
                onClick = { onNumberClick("3") },
                modifier = Modifier.weight(1f)
            )
            CalculatorButton(
                symbol = "+",
                type = CalculatorButtonType.OPERATION,
                onClick = { onOperationClick(Operation.ADD) },
                modifier = Modifier.weight(1f)
            )
        }

        // Пятый ряд: 0, ., =
        Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorWideButton(
                symbol = "0",
                type = CalculatorButtonType.NUMBER,
                onClick = { onNumberClick("0") },
                modifier = Modifier.weight(2f)
            )
            CalculatorButton(
                symbol = ".",
                type = CalculatorButtonType.NUMBER,
                onClick = onDecimalClick,
                modifier = Modifier.weight(1f)
            )
            CalculatorButton(
                symbol = "=",
                type = CalculatorButtonType.EQUALS,
                onClick = onEqualsClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
