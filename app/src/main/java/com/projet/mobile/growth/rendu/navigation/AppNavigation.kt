package com.projet.mobile.growth.rendu.navigation

import com.projet.mobile.growth.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation3.runtime.NavKey
import com.projet.mobile.growth.rendu.views.ChronoScreen
import com.projet.mobile.growth.rendu.views.HomeScreen
import com.projet.mobile.growth.rendu.views.SearchScreen
import com.projet.mobile.growth.rendu.viewModel.AppViewModel
import com.projet.mobile.growth.rendu.views.AddActivityScreen
import com.projet.mobile.growth.rendu.views.AddTrainingScreen
import com.projet.mobile.growth.rendu.views.TrainingsScreen
import kotlinx.serialization.Serializable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.projet.mobile.growth.rendu.views.ExerciseDetailsScreen


@Serializable data object Home : NavKey
@Serializable data object Search : NavKey
@Serializable data object Chrono : NavKey
@Serializable data object Trainings : NavKey

@Serializable data class AddActivity(val day: Int) : NavKey
@Serializable data class ExerciseDetails(val id : String) : NavKey
@Serializable data object AddTraining : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(vm: AppViewModel) {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val weekPlan by vm.weeklyPlan.collectAsState()
    val trainings by vm.trainings.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.ic_dumbbell),
                            contentDescription = null
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Growth")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentDestination?.hasRoute<Home>() == true,
                    onClick = {
                        navController.navigate(Home) {
                            popUpTo(Home)
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = currentDestination?.hasRoute<Trainings>() == true,
                    onClick = {
                        navController.navigate(Trainings) {
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Default.Create, null) },
                    label = { Text("Trainings") }
                )
                NavigationBarItem(
                    selected = currentDestination?.hasRoute<Search>() == true,
                    onClick = {
                        navController.navigate(Search) {
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Default.Search, null) },
                    label = { Text("Search") }
                )
                NavigationBarItem(
                    selected = currentDestination?.hasRoute<Chrono>() == true,
                    onClick = {
                        navController.navigate(Chrono) {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            painterResource(R.drawable.outline_timer_24),
                            null
                        )
                    },
                    label = { Text("Chrono") }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Home,
            modifier = Modifier.padding(padding)
        ) {

            composable<Home> {
                HomeScreen(
                    navController = navController,
                    weekPlan = weekPlan,
                    onDel = { toDel, day -> vm.removeTrainingFromDay(toDel, day) }
                )
            }

            composable<Search> {
                SearchScreen(
                    onExerciseClick = {
                        navController.navigate(ExerciseDetails(it.id))
                    }
                )
            }

            composable<Chrono> {
                ChronoScreen()
            }

            composable<Trainings> {
                TrainingsScreen(
                    list = trainings,
                    navController = navController,
                    onDelete = { toDel -> vm.deleteTraining(toDel) },
                )
            }

            composable<AddTraining> {
                AddTrainingScreen(
                    navController = navController,
                    onSave = { vm.addNewTraining(it) }
                )
            }

            composable<AddActivity> { entry ->
                val args = entry.toRoute<AddActivity>()
                AddActivityScreen(
                    list = trainings,
                    navController = navController,
                    onSave = {
                        vm.addTrainingsToDay(
                            it,
                            args.day
                        )
                    }
                )
            }

            composable<ExerciseDetails> { entry ->
                val args = entry.toRoute<ExerciseDetails>()
                ExerciseDetailsScreen(
                    exerciseId = args.id,
                    navController = navController
                )
            }
        }
    }
}
