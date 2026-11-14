package cl.duoc.tutorvirtual.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cl.duoc.tutorvirtual.data.model.EstadoHito
import cl.duoc.tutorvirtual.data.model.Hito

class TutorViewModel : ViewModel() {

    // 1. Estado para el buscador de Preguntas Frecuentes (FAQ)
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    // 2. Estado para "Mis Avances" (Hitos)
    private val _hitos = mutableStateOf(listOf(
        Hito(1, "Planteamiento del Problema", "Definición y justificación", "15/11/2026", EstadoHito.POR_INICIAR),
        Hito(2, "Marco Teórico", "Revisión bibliográfica", "30/11/2026", EstadoHito.POR_INICIAR)
    ))
    val hitos: State<List<Hito>> = _hitos

    fun actualizarEstadoHito(id: Int, nuevoEstado: EstadoHito) {
        _hitos.value = _hitos.value.map {
            if (it.id == id) it.copy(estado = nuevoEstado) else it
        }
    }

    // 3. Estado para "Mi Perfil" con validación de formulario
    private val _nombreEstudiante = mutableStateOf("")
    val nombreEstudiante: State<String> = _nombreEstudiante

    private val _errorNombre = mutableStateOf(false)
    val errorNombre: State<Boolean> = _errorNombre

    fun onNombreChanged(nuevoNombre: String) {
        _nombreEstudiante.value = nuevoNombre
        _errorNombre.value = nuevoNombre.isBlank()
    }
}