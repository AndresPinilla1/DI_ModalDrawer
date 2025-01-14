package com.example.di_modaldrawer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.di_modaldrawer.ui.theme.DI_ModalDrawerTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DI_ModalDrawerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Aplicacion() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer(navController = navController, cerrarDrawer = { scope.launch { drawerState.close() } })
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Mi Aplicación") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir Drawer"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Gray)
                )
            }
        ) { paddingValues ->
            Navegacion(navController = navController, modifier = Modifier.padding(paddingValues))
        }
    }
}

@Composable
fun Drawer(navController: NavHostController, cerrarDrawer: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Navegación",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Button(
            onClick = {
                navController.navigate("pantalla_principal")
                cerrarDrawer()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Pantalla Principal")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                navController.navigate("pantalla_configuracion")
                cerrarDrawer()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Configuración")
        }
    }
}


@Composable
fun Navegacion(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "pantalla_principal") {
        composable("pantalla_principal") {
            PantallaPrincipal(modifier = modifier)
        }
        composable("pantalla_configuracion") {
            PantallaConfiguracion(modifier = modifier)
        }
    }
}

@Composable
fun PantallaPrincipal(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Bienvenido a la aplicación")
    }
}

@Composable
fun PantallaConfiguracion(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Configuración de la App")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Aplicacion()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DI_ModalDrawerTheme {
        Greeting("Android")
    }
}