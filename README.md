# Pulso de Riego - Android app

Aplicación inicial (esqueleto) para "Pulso de Riego".

Stack:
- Kotlin
- Jetpack Compose (Material3)
- Room
- MVVM (ViewModel + Repository)

Cómo compilar

1. Abrir en Android Studio (recomendado Android Studio Flamingo o posterior).
2. minSdk 24, compileSdk 34.
3. Ejecutar "Build > Make Project".

Archivos añadidos
- Estructura básica de app: MainActivity, UI composables, Room entities, DAO y DB, ViewModel, Repository.

Notas
- No incluí el gradle wrapper por tamaño; si quieres lo agrego en un commit separado.
- Las credenciales de prueba se pre-poblan en la base de datos al crear la DB: admin/admin, ing_riego/1234, regador/1234
