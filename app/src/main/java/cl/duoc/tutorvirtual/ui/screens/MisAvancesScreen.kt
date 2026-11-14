package cl.duoc.tutorvirtual.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cl.duoc.tutorvirtual.data.model.EstadoHito
import cl.duoc.tutorvirtual.utils.NotificacionHelper
import cl.duoc.tutorvirtual.viewmodel.TutorViewModel

@Composable
fun MisAvancesScreen(viewModel: TutorViewModel) {
    val hitos = viewModel.hitos.value

    // 1. Instanciar el helper de notificaciones con el contexto actual
    val context = LocalContext.current
    val notificacionHelper = remember { NotificacionHelper(context) }

    // 2. Lanzador para solicitar permiso de notificaciones (Android 13+)
    val permisoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // Aquí podrías manejar el caso en que el usuario deniegue el permiso
    }

    // 3. Solicitar permiso automáticamente al entrar a la pantalla
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permisoLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Mis Avances", style = MaterialTheme.typography.headlineSmall)
        Text("Gestiona el estado de entrega de los hitos de tu Trabajo de Título.")

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(hitos) { hito ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = hito.titulo,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(text = hito.descripcion, style = MaterialTheme.typography.bodyMedium)
                        Text(
                            text = "Fecha límite: ${hito.fechaLimite}",
                            style = MaterialTheme.typography.labelMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            EstadoHito.entries.forEach { estado ->
                                FilterChip(
                                    selected = hito.estado == estado,
                                    onClick = {
                                        viewModel.actualizarEstadoHito(hito.id, estado)

                                        // 4. Disparar notificación al marcar como ENVIADO
                                        if (estado == EstadoHito.ENVIADO) {
                                            notificacionHelper.mostrarNotificacion(
                                                titulo = "Hito Actualizado",
                                                mensaje = "Has marcado '${hito.titulo}' como Enviado."
                                            )
                                        }
                                    },
                                    label = {
                                        Text(
                                            text = estado.name.replace("_", " "),
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}