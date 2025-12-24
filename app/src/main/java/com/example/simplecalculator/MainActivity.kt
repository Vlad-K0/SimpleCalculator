package com.example.simplecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.simplecalculator.presentation.CalculatorViewModel
import com.example.simplecalculator.screen.CalculatorScreen
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

/**
 * Точка входа в приложение.
 * Наследуется от ComponentActivity для поддержки Jetpack Compose.
 */
class MainActivity : ComponentActivity() {

    // Инициализация ViewModel через делегат viewModels()
    private val viewModel: CalculatorViewModel by viewModels()

    /**
     * Основной метод жизненного цикла Activity.
     * Здесь настраивается контент экрана (UI).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}