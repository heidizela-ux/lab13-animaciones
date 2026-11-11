package com.example.lab13_animaciones

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
fun Ejercicio3() {
    var activado by remember { mutableStateOf(false) }

    val tamano by animateDpAsState(
        targetValue = if (activado) 140.dp else 70.dp,
        animationSpec = tween(durationMillis = 700),
        label = "tamano"
    )
    val posX by animateDpAsState(
        targetValue = if (activado) 150.dp else 0.dp,
        animationSpec = tween(durationMillis = 700),
        label = "posX"
    )
    val posY by animateDpAsState(
        targetValue = if (activado) 40.dp else 0.dp,
        animationSpec = tween(durationMillis = 700),
        label = "posY"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { activado = !activado }) {
            Text("Mover y cambiar tamaño")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Orden correcto: offset -> size -> background")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Box(
                modifier = Modifier
                    .offset(x = posX, y = posY)
                    .size(tamano)
                    .background(Color(0xFFE53935))
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Orden invertido: size -> background -> offset")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(tamano)
                    .background(Color(0xFFFB8C00))
                    .offset(x = posX, y = posY)
            )
        }
    }
}