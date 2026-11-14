package cl.duoc.tutorvirtual.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.tutorvirtual.viewmodel.TutorViewModel

@Composable
fun PreguntasFrecuentesScreen(viewModel: TutorViewModel) {
    // Escuchamos el estado reactivo que viene del ViewModel
    val query = viewModel.searchQuery.value

    // Banco de preguntas de demostración
    val todasLasPreguntas = listOf(
        "¿Qué edición de normas APA se utiliza?" to "Se utiliza Normas APA 7ª edición para citas y referencias.",
        "¿Cuáles son los capítulos del avance 1?" to "Portada, Índice, Planteamiento del problema y Marco Teórico preliminar.",
        "¿En qué tiempo verbal se redacta?" to "En el proyecto de tesis en futuro, en el informe final en pasado.",
        "¿Qué es el enfoque cualitativo?" to "Aquel que explora percepciones y fenómenos en profundidad sin medición numérica."
    )

    // Lógica de filtrado en tiempo real
    val preguntasFiltradas = if (query.isBlank()) {
        todasLasPreguntas
    } else {
        todasLasPreguntas.filter {
            it.first.contains(query, ignoreCase = true) || it.second.contains(query, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Preguntas Frecuentes", style = MaterialTheme.typography.headlineSmall)
        Text("Resuelve tus dudas sobre metodología, formato APA y citación.")

        // Campo de búsqueda validado visualmente
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.onSearchQueryChanged(it) },
            label = { Text("Buscar palabra clave...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Lista dinámica de resultados
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(preguntasFiltradas) { (pregunta, respuesta) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(pregunta, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(respuesta, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
            if (preguntasFiltradas.isEmpty()) {
                item {
                    Text(
                        "No se encontraron resultados para \"$query\".",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}