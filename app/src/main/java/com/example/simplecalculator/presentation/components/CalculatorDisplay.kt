package com.example.simplecalculator.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Экран дисплея калькулятора.
 * Отображает текущее введенное число или результат.
 * 
 * @param displayValue Основной текст для отображения (число).
 * @param expression Текст выражения (сейчас не используется, передается пустой строкой).
 * @param modifier Модификатор контейнера.
 */
@Composable
fun CalculatorDisplay(
    displayValue: String,
    expression: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        // Выражение (мелким шрифтом сверху)
        if (expression.isNotEmpty()) {
            Text(
                text = expression,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.End,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Основное значение (крупным шрифтом)
        Text(
            text = displayValue,
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = calculateFontSize(displayValue),
                fontWeight = FontWeight.Normal
            ),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/**
 * Вычисление динамического размера шрифта в зависимости от длины текста.
 * Позволяет уменьшать шрифт для длинных чисел, чтобы они помещались на экране.
 * 
 * @param text Текст для отображения.
 * @return Размер шрифта (TextUnit).
 */
private fun calculateFontSize(text: String): androidx.compose.ui.unit.TextUnit {
    return when {
        text.length <= 6 -> 72.sp
        text.length <= 9 -> 56.sp
        text.length <= 12 -> 44.sp
        else -> 34.sp
    }
}
