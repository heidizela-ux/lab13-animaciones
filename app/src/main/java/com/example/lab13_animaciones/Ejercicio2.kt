package com.example.lab13_animaciones

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Ejercicio2() {
    var esAzul by remember { mutableStateOf(true) }
    var usarSpring by remember { mutableStateOf(false) }

    val colorObjetivo = if (esAzul) Color(0xFF1E88E5) else Color(0xFF43A047)

    val colorAnimado by animateColorAsState(
        targetValue = colorObjetivo,
        animationSpec = if (usarSpring) {
            spring<Color>(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        } else {
            tween<Color>(durationMillis = 1000)
        },
        label = "colorCuadro"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { esAzul = !esAzul }) {
            Text("Cambiar color (azul / verde)")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { usarSpring = !usarSpring }) {
            Text(if (usarSpring) "Animación actual: spring" else "Animación actual: tween")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Con animateColorAsState")
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(colorAnimado)
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text("Sin animación (cambio instantáneo)")
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(colorObjetivo)
        )
    }
}