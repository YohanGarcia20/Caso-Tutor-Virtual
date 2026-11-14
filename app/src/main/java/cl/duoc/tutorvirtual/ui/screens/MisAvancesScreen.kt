package cl.duoc.tutorvirtual.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import cl.duoc.tutorvirtual.data.model.EstadoHito
import cl.duoc.tutorvirtual.utils.NotificacionHelper
import cl.duoc.tutorvirtual.viewmodel.TutorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisAvancesScreen(viewModel: TutorViewModel) {
    // 1. Agregamos el .value para que reconozca la lista de Hitos correctamente
    val hitos = viewModel.hitos.value

    val context = LocalContext.current
    val notificacionHelper = remember { NotificacionHelper(context) }
    val haptic = LocalHapticFeedback.current

    val permisoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { }

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize( // Animación de tamaño al cambiar estado
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        ),
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

                        // 2. Volvemos al uso de FilterChips llamando a actualizarEstadoHito
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            EstadoHito.entries.forEach { estado ->
                                FilterChip(
                                    selected = hito.estado == estado,
                                    onClick = {
                                        // Dispara vibración táctil
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                                        // Actualiza el estado con la función que ya existe en tu ViewModel
                                        viewModel.actualizarEstadoHito(hito.id, estado)

                                        // Dispara la notificación si el estado es ENVIADO
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