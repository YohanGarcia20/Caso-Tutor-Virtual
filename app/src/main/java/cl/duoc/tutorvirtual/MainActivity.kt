package cl.duoc.tutorvirtual

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.tutorvirtual.ui.screens.GuiaMetodologicaScreen
import cl.duoc.tutorvirtual.ui.screens.PreguntasFrecuentesScreen
import cl.duoc.tutorvirtual.viewmodel.TutorViewModel
import cl.duoc.tutorvirtual.ui.screens.MisAvancesScreen
import cl.duoc.tutorvirtual.ui.screens.MiPerfilScreen
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { TutorVirtualApp() } }
    }
}

@Composable
fun TutorVirtualApp(viewModel: TutorViewModel = viewModel()) {
    var pantalla by remember { mutableStateOf("Inicio") }

    BackHandler(enabled = pantalla != "Inicio") {
        pantalla = "Inicio"
    }

    Scaffold(
        topBar = {
            Surface(tonalElevation = 3.dp) {
                Text(
                    "Tutor Virtual ESUCAR",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                )
            }
        }
    ) { padding ->
        // Contenedor principal estructurado sin verticalScroll
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when (pantalla) {
                "Inicio" -> {
                    Text("Bienvenido al Tutor Virtual", style = MaterialTheme.typography.headlineSmall)
                    Text("Apoyo para Trabajo de Título I, II y III.")
                    listOf("Guía metodológica", "Preguntas frecuentes", "Mis avances", "Mi perfil").forEach { destino ->
                        Button(
                            onClick = { pantalla = destino },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text(destino) }
                    }
                }
                "Guía metodológica" -> {
                    Box(modifier = Modifier.weight(1f)) {
                        GuiaMetodologicaScreen()
                    }
                    OutlinedButton(onClick = { pantalla = "Inicio" }, modifier = Modifier.fillMaxWidth()) {
                        Text("Volver al inicio")
                    }
                }
                "Preguntas frecuentes" -> {
                    Box(modifier = Modifier.weight(1f)) {
                        PreguntasFrecuentesScreen(viewModel = viewModel)
                    }
                    OutlinedButton(onClick = { pantalla = "Inicio" }, modifier = Modifier.fillMaxWidth()) {
                        Text("Volver al inicio")
                    }
                }
                "Mis avances" -> {
                    Box(modifier = Modifier.weight(1f)) {
                        MisAvancesScreen(viewModel = viewModel)
                    }
                    OutlinedButton(onClick = { pantalla = "Inicio" }, modifier = Modifier.fillMaxWidth()) {
                        Text("Volver al inicio")
                    }
                }
                "Mi perfil" -> {
                    Box(modifier = Modifier.weight(1f)) {
                        MiPerfilScreen(viewModel = viewModel)
                    }
                    OutlinedButton(onClick = { pantalla = "Inicio" }, modifier = Modifier.fillMaxWidth()) {
                        Text("Volver al inicio")
                    }
                }
                else -> {
                    Text(pantalla, style = MaterialTheme.typography.headlineMedium)
                    Text("Módulo preparado para próximas actividades.")
                    OutlinedButton(onClick = { pantalla = "Inicio" }) {
                        Text("Volver al inicio")
                    }
                }
            }
        }
    }
}