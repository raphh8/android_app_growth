package com.projet.mobile.growth.rendu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.projet.mobile.growth.rendu.navigation.AppNavigation
import com.projet.mobile.growth.rendu.viewModel.AppViewModel
import com.projet.mobile.growth.ui.theme.GrowthTheme

class RenduActivity : ComponentActivity() {
    lateinit var vm : AppViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        vm = AppViewModel(application)
        setContent {
            GrowthTheme {
                AppNavigation(vm)
            }
        }
    }
}
