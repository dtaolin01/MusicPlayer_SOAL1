package com.example.musicplayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%d:%02d", minutes, remainingSeconds)
}

@Composable
fun MusicPlayerScreen() {
    val backgroundColor = Color(0xFF0F172A)
    val lyricsBgColor = Color(0xFF1E293B)
    val accentColor = Color(0xFF38BDF8)
    val textColorPrimary = Color(0xFFFFFFFF)
    val textColorSecondary = Color(0xFF94A3B8)

    val totalDurationSeconds = 165
    var currentPositionSeconds by remember { mutableFloatStateOf(12f) }
    var isPlaying by remember { mutableStateOf(true) }

    LaunchedEffect(isPlaying) {
        while (isPlaying && currentPositionSeconds < totalDurationSeconds) {
            delay(1000L)
            currentPositionSeconds += 1f
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Back",
                    tint = textColorPrimary
                )
            }
            Text(
                text = "Liked Songs",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = textColorPrimary
            )
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = "More Options",
                    tint = textColorPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.songcover),
                contentDescription = "Album Cover",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "What If I Call",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColorPrimary
                )
                Text(
                    text = "Alex Crichton",
                    fontSize = 16.sp,
                    color = textColorSecondary
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Like",
                    tint = accentColor,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val progress = currentPositionSeconds / totalDurationSeconds
        val currentSecInt = currentPositionSeconds.toInt()
        val remainingSecInt = totalDurationSeconds - currentSecInt

        Column(modifier = Modifier.fillMaxWidth()) {
            Slider(
                value = progress.coerceIn(0f, 1f),
                onValueChange = { newProgress ->
                    currentPositionSeconds = newProgress * totalDurationSeconds
                },
                colors = SliderDefaults.colors(
                    thumbColor = accentColor,
                    activeTrackColor = accentColor,
                    inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatTime(currentSecInt),
                    fontSize = 12.sp,
                    color = textColorSecondary
                )
                Text(
                    text = "-${formatTime(remainingSecInt)}",
                    fontSize = 12.sp,
                    color = textColorSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                currentPositionSeconds = 0f
            }) {
                Icon(
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = "Previous",
                    modifier = Modifier.size(36.dp),
                    tint = textColorPrimary
                )
            }
            IconButton(
                onClick = {
                    isPlaying = !isPlaying
                },
                modifier = Modifier
                    .size(56.dp)
                    .background(accentColor, shape = RoundedCornerShape(28.dp))
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = "Next",
                    modifier = Modifier.size(36.dp),
                    tint = textColorPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(lyricsBgColor)
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Lyrics",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColorPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = """
                        [Verse 1]
                        So what if I call?
                        And you pick up the phone?
                        And I use this holiday
                        To make my way to your ghost

                        [Verse 2]
                        You walked in the party, your coat was untied
                        Slammin' the door 'cause it's colder outside
                        Now I won't forget that moment like the blink of an eye
                        You're off to the beach now 'til the weather gets nice

                        [Chorus]
                        But what if I call?
                        And you pick up the phone?
                        And I use this holiday to make my way to your ghost
                        But, what if you're lonely?
                        And you know I am too?
                        And I get the chance to say
                        "I wish I didn't, but I miss you"
                        I miss you

                        [Bridge]
                        You know it's true
                        Yeah, I miss you
                        You know it's true

                        [Chorus]
                        So, what if I call?
                        And you pick up the phone?
                        And I use this holiday to make my way to your ghost
                        But, what if you're lonely?
                        And you know I am too?
                        And I get the chance to say
                        "I wish I didn't, but I miss you"
                        I miss you
                    """.trimIndent(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColorPrimary.copy(alpha = 0.9f),
                    lineHeight = 24.sp
                )
            }
        }
    }
}