package com.example.lab13_animaciones

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun EjercicioFinal() {
    val anchoCarril = 100.dp
    val altoJuego = 380.dp
    val tamanoObjeto = 60.dp

    var carrilJugador by remember { mutableIntStateOf(1) }
    var carrilMeteoro by remember { mutableIntStateOf(Random.nextInt(3)) }
    var vidas by remember { mutableIntStateOf(3) }
    var puntaje by remember { mutableIntStateOf(0) }
    var juegoTerminado by remember { mutableStateOf(false) }
    var reinicio by remember { mutableIntStateOf(0) }
    val caida = remember { Animatable(0f) }

    LaunchedEffect(reinicio) {
        while (vidas > 0) {
            carrilMeteoro = Random.nextInt(3)
            caida.snapTo(0f)
            val duracion = (2200 - puntaje * 10).coerceAtLeast(900)
            caida.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = duracion, easing = LinearEasing)
            )
            if (carrilMeteoro == carrilJugador) {
                vidas -= 1
            } else {
                puntaje += 10
            }
        }
        juegoTerminado = true
    }

    val infinita = rememberInfiniteTransition(label = "infinita")
    val brillo by infinita.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
        label = "brillo"
    )
    val giro by infinita.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(1500, easing = LinearEasing)),
        label = "giro"
    )

    val xJugador by animateDpAsState(
        targetValue = anchoCarril * carrilJugador + (anchoCarril - tamanoObjeto) / 2,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "xJugador"
    )

    val fraccionVida by animateFloatAsState(targetValue = vidas / 3f, label = "fraccionVida")
    val colorVida by animateColorAsState(
        targetValue = when (vidas) {
            3 -> Color(0xFF43A047)
            2 -> Color(0xFFFDD835)
            else -> Color(0xFFE53935)
        },
        label = "colorVida"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Esquiva el meteoro", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        // HUD: vida y puntaje
        Row(
            modifier = Modifier.width(anchoCarril * 3),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(130.dp)
                    .height(14.dp)
                    .background(Color.DarkGray, RoundedCornerShape(7.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraccionVida)
                        .background(colorVida, RoundedCornerShape(7.dp))
                )
            }
            AnimatedContent(
                targetState = puntaje,
                transitionSpec = {
                    (slideInVertically(initialOffsetY = { it }) + fadeIn()) togetherWith
                            (slideOutVertically(targetOffsetY = { -it }) + fadeOut())
                },
                label = "puntaje"
            ) { valor ->
                Text("Puntaje: $valor", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .size(width = anchoCarril * 3, height = altoJuego)
                .background(Color(0xFF0D1B2A))
        ) {
            repeat(8) { i ->
                Box(
                    modifier = Modifier
                        .offset(x = ((i * 37) % 290).dp, y = ((i * 61) % 360).dp)
                        .size(4.dp)
                        .alpha(brillo)
                        .background(Color.White, CircleShape)
                )
            }

            Box(
                modifier = Modifier
                    .offset(
                        x = anchoCarril * carrilMeteoro + (anchoCarril - tamanoObjeto) / 2,
                        y = (altoJuego - tamanoObjeto) * caida.value
                    )
                    .size(tamanoObjeto)
                    .rotate(giro)
                    .background(Color(0xFFFF7043), RoundedCornerShape(14.dp))
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = xJugador)
                    .size(tamanoObjeto)
                    .background(Color(0xFF29B6F6), RoundedCornerShape(12.dp))
            )


            androidx.compose.animation.AnimatedVisibility(
                visible = juegoTerminado,
                modifier = Modifier.align(Alignment.Center),
                enter = fadeIn(tween(500)) + scaleIn(),
                exit = fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .background(Color(0xCC000000), RoundedCornerShape(16.dp))
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("GAME OVER", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Puntaje final: $puntaje", color = Color.White)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = {
                        vidas = 3
                        puntaje = 0
                        juegoTerminado = false
                        reinicio += 1
                    }) { Text("Reintentar") }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                enabled = !juegoTerminado,
                onClick = { carrilJugador = (carrilJugador - 1).coerceAtLeast(0) }
            ) { Text("◀ Izquierda") }
            Button(
                enabled = !juegoTerminado,
                onClick = { carrilJugador = (carrilJugador + 1).coerceAtMost(2) }
            ) { Text("Derecha ▶") }
        }
    }
}