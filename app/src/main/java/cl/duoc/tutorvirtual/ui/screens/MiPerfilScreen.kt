package cl.duoc.tutorvirtual.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.tutorvirtual.viewmodel.TutorViewModel

@Composable
fun MiPerfilScreen(viewModel: TutorViewModel) {
    val nombre = viewModel.nombreEstudiante.value
    val error = viewModel.errorNombre.value

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Mi Perfil", style = MaterialTheme.typography.headlineSmall)
        Text("Actualiza tus datos para personalizar el Tutor Virtual.")

        OutlinedTextField(
            value = nombre,
            onValueChange = { viewModel.onNombreChanged(it) },
            label = { Text("Nombre completo") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Icono perfil") },
            isError = error,
            trailingIcon = {
                if (error) {
                    Icon(Icons.Default.Error, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                }
            },
            supportingText = {
                if (error) {
                    Text("El nombre es obligatorio", color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = { /* Acción de guardar (se implementará más adelante) */ },
            modifier = Modifier.fillMaxWidth(),
            enabled = !error && nombre.isNotBlank()
        ) {
            Text("Guardar perfil")
        }
    }
}