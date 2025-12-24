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
 * Дисплей калькулятора с выражением и результатом
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
 * Вычисление размера шрифта в зависимости от длины текста
 */
private fun calculateFontSize(text: String): androidx.compose.ui.unit.TextUnit {
    return when {
        text.length <= 6 -> 72.sp
        text.length <= 9 -> 56.sp
        text.length <= 12 -> 44.sp
        else -> 34.sp
    }
}
