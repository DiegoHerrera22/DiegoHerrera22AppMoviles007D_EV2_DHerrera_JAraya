plugins {
    // Declaramos los plugins de Android y Kotlin con versiones compatibles para API 16.
    id("com.android.application") version "8.6.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
}

// Este archivo no requiere más configuración porque delegamos al módulo :app.