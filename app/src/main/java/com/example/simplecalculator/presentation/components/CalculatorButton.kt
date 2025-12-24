package com.example.simplecalculator.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Типы кнопок калькулятора (влияют на цветовое оформление)
 */
enum class CalculatorButtonType {
    /** Цифровые кнопки (0-9, точка) - темно-серые */
    NUMBER,
    /** Основные операции (+, -, *, /) - оранжевые/акцентные */
    OPERATION,
    /** Функциональные кнопки (AC, +/-, %) - светло-серые */
    FUNCTION,
    /** Кнопка равно (=) - выделенная цветом */
    EQUALS
}

/**
 * Стандартная круглая кнопка калькулятора.
 * 
 * @param symbol Текст на кнопке (число или символ операции).
 * @param type Тип кнопки для определения цвета.
 * @param onClick Обработчик нажатия.
 * @param modifier Модификатор для настройки размера и отступов.
 */
@Composable
fun CalculatorButton(
    symbol: String,
    type: CalculatorButtonType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor: Color
    val contentColor: Color

    when (type) {
        CalculatorButtonType.NUMBER -> {
            containerColor = MaterialTheme.colorScheme.surfaceVariant
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        }
        CalculatorButtonType.OPERATION -> {
            containerColor = MaterialTheme.colorScheme.primaryContainer
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        }
        CalculatorButtonType.FUNCTION -> {
            containerColor = MaterialTheme.colorScheme.secondaryContainer
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        }
        CalculatorButtonType.EQUALS -> {
            containerColor = MaterialTheme.colorScheme.primary
            contentColor = MaterialTheme.colorScheme.onPrimary
        }
    }

    Button(
        onClick = onClick,
        modifier = modifier.fillMaxSize(), // Кнопка заполняет выделенную ей ячейку
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = symbol,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

/**
 * Широкая кнопка калькулятора (обычно для "0").
 * Занимает место двух обычных кнопок. Выравнивание текста по левому краю.
 *
 * @param symbol Текст на кнопке ("0").
 * @param type Тип кнопки (обычно NUMBER).
 * @param onClick Обработчик нажатия.
 * @param modifier Модификатор (должен использовать weight(2f) в родителе).
 */
@Composable
fun CalculatorWideButton(
    symbol: String,
    type: CalculatorButtonType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor: Color
    val contentColor: Color

    when (type) {
        CalculatorButtonType.NUMBER -> {
            containerColor = MaterialTheme.colorScheme.surfaceVariant
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        }
        else -> {
            containerColor = MaterialTheme.colorScheme.surfaceVariant
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        }
    }

    FilledTonalButton(
        onClick = onClick,
        modifier = modifier.fillMaxSize(),
        shape = CircleShape,
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp), // Смещаем текст влево, как в оригинальном калькуляторе
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = symbol,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
