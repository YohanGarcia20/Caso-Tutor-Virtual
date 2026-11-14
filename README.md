# Tutor Virtual ESUCAR 

Prototipo funcional de aplicación móvil (MVP) desarrollado en Kotlin y Jetpack Compose para guiar paso a paso a los estudiantes de la asignatura Trabajo de Título I, II y III de ESUCAR, centralizando orientaciones metodológicas, preguntas frecuentes y seguimiento de hitos.

## Integrantes del Equipo
* **Yohan Garcia**
* **Sebastian Salinas**
* **Equipo Genios Delirantes:**

## Funcionalidades Implementadas
* **Guía Metodológica Offline:** Consulta local sin conexión a internet de las 5 etapas fundamentales (Planteamiento, Marco Teórico, Metodología, Recogida de información y Análisis de resultados), cada una con descripción y orientaciones prácticas.
* **Buscador de Preguntas Frecuentes (FAQ):** Filtrado reactivo en tiempo real por palabras clave sobre estructura de avances, Normas APA 7ª edición y redacción académica.
* **Gestión de Hitos (Mis Avances):** Seguimiento interactivo del estado de entrega (*Por iniciar*, *En desarrollo*, *Enviado*, *Aprobado*) administrado mediante MVVM.
* **Mi Perfil:** Formulario con validaciones visuales en tiempo real, mensajes de error e íconos de retroalimentación.
* **Recursos Nativos y Animaciones:**
  * *Notificaciones Locales:* Alertas del sistema al registrar el envío de un hito académico (con gestión de permisos en tiempo de ejecución).
  * *Retroalimentación Háptica:* Respuesta física mediante el motor de vibración del dispositivo al interactuar con los controles.
  * *Animaciones Funcionales:* Transiciones fluidas (`animateContentSize`) en las tarjetas de estado.

## Arquitectura y Tecnologías
* **Lenguaje:** Kotlin
* **Interfaz de Usuario:** Jetpack Compose (Material Design 3)
* **Arquitectura:** MVVM (`Model - View - ViewModel`) con separación modular de paquetes (`data`, `ui`, `viewmodel`)
* **SDK Mínimo:** API 26 (Android 8.0)
* **Configuración de Compilación:** Gradle con Kotlin DSL

## Instrucciones de Ejecución
1. Clonar este repositorio en el equipo local:
   git clone https://github.com/YohanGarcia20/Caso-Tutor-Virtual.git
   
2. Abrir Android Studio y seleccionar File → Open, eligiendo la carpeta raíz del proyecto clonado.

3. Esperar a que finalice la sincronización automática de Gradle.

4. Conectar un dispositivo Android físico (con Depuración USB habilitada) o iniciar un emulador con API 26 o superior.

5. Presionar el botón Run en la barra superior para compilar e instalar la aplicación.
