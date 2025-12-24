package com.example.simplecalculator.screen

import com.example.simplecalculator.presentation.components.CalculatorKeypad
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.simplecalculator.presentation.CalculatorViewModel
import com.example.simplecalculator.presentation.components.CalculatorDisplay


/**
 * Главный экран калькулятора, управляющий адаптивным layout.
 * Выбирает между портретной и ландшафтной версткой в зависимости от конфигурации устройства.
 *
 * @param viewModel ViewModel для управления состоянием.
 * @param modifier Модификатор корневого элемента.
 */
@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val configuration = LocalConfiguration.current

    Surface(modifier = modifier.fillMaxSize()) {
        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                // Горизонтальная ориентация: вертикальный layout с компактным дисплеем
                LandscapeLayout(
                    displayValue = uiState.displayValue,
                    expression = uiState.expression,
                    viewModel = viewModel
                )
            }
            else -> {
                // Вертикальная ориентация: дисплей сверху, клавиатура снизу
                PortraitLayout(
                    displayValue = uiState.displayValue,
                    expression = uiState.expression,
                    viewModel = viewModel
                )
            }
        }
    }
}

/**
 * Верстка для вертикальной (портретной) ориентации.
 * Экран делится на:
 * - Дисплей: 35% высоты.
 * - Клавиатура: 65% высоты.
 */
@Composable
private fun PortraitLayout(
    displayValue: String,
    expression: String,
    viewModel: CalculatorViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Дисплей (35% высоты)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.35f),
            contentAlignment = Alignment.BottomEnd
        ) {
            CalculatorDisplay(
                displayValue = displayValue,
                expression = expression
            )
        }

        // Клавиатура (65% высоты)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.65f),
            contentAlignment = Alignment.BottomCenter
        ) {
            CalculatorKeypad(
                onNumberClick = viewModel::onNumberClick,
                onOperationClick = viewModel::onOperationClick,
                onEqualsClick = viewModel::onEqualsClick,
                onClearClick = viewModel::onClearClick,
                onDeleteClick = viewModel::onDeleteClick,
                onDecimalClick = viewModel::onDecimalClick,
                onPercentClick = viewModel::onPercentClick,
                onNegateClick = viewModel::onNegateClick
            )
        }
    }
}

/**
 * Верстка для горизонтальной (ландшафтной) ориентации.
 * Использует вертикальный Column, но с измененными пропорциями для лучшего вида:
 * - Дисплей: 35% высоты.
 * - Клавиатура: 65% высоты.
 * Несмотря на "горизонтальность" экрана, сохраняется вертикальная структура калькулятора.
 */
@Composable
private fun LandscapeLayout(
    displayValue: String,
    expression: String,
    viewModel: CalculatorViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Дисплей сверху (35% высоты)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.35f),
            contentAlignment = Alignment.BottomEnd
        ) {
            CalculatorDisplay(
                displayValue = displayValue,
                expression = expression
            )
        }

        // Клавиатура снизу (65% высоты)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.65f),
            contentAlignment = Alignment.Center
        ) {
            CalculatorKeypad(
                onNumberClick = viewModel::onNumberClick,
                onOperationClick = viewModel::onOperationClick,
                onEqualsClick = viewModel::onEqualsClick,
                onClearClick = viewModel::onClearClick,
                onDeleteClick = viewModel::onDeleteClick,
                onDecimalClick = viewModel::onDecimalClick,
                onPercentClick = viewModel::onPercentClick,
                onNegateClick = viewModel::onNegateClick
            )
        }
    }
}
