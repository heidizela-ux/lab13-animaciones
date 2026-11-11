package com.example.lab13_animaciones

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class EstadoPantalla { CARGANDO, CONTENIDO, ERROR }

@Composable
fun Ejercicio4() {
    var estado by remember { mutableStateOf(EstadoPantalla.CARGANDO) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Button(onClick = { estado = EstadoPantalla.CARGANDO }) { Text("Cargando") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { estado = EstadoPantalla.CONTENIDO }) { Text("Contenido") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { estado = EstadoPantalla.ERROR }) { Text("Error") }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            scope.launch {
                estado = EstadoPantalla.CARGANDO
                delay(2000)
                estado = EstadoPantalla.CONTENIDO
            }
        }) { Text("Simular carga") }

        Spacer(modifier = Modifier.height(32.dp))

        AnimatedContent(
            targetState = estado,
            transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 700, delayMillis = 300)) togetherWith
                        fadeOut(animationSpec = tween(durationMillis = 300))
            },
            label = "estadoPantalla"
        ) { estadoActual ->
            when (estadoActual) {
                EstadoPantalla.CARGANDO -> Box(
                    modifier = Modifier
                        .size(width = 280.dp, height = 160.dp)
                        .background(Color(0xFFE3F2FD), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Cargando datos...")
                    }
                }

                EstadoPantalla.CONTENIDO -> Box(
                    modifier = Modifier
                        .size(width = 280.dp, height = 160.dp)
                        .background(Color(0xFFC8E6C9), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("¡Contenido cargado con éxito!")
                }

                EstadoPantalla.ERROR -> Box(
                    modifier = Modifier
                        .size(width = 280.dp, height = 160.dp)
                        .background(Color(0xFFFFCDD2), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: no se pudo cargar la información")
                }
            }
        }
    }
}
