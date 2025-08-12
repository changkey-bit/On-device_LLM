package com.google.mediapipe.examples.llminference.ui.screen

import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.mediapipe.examples.llminference.R

@Composable
fun SelectionRoute(
    onModelSelected: () -> Unit
) {
    val sun_Font = FontFamily(
        Font(R.font.sun_l)
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 배경 이미지
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 버튼 (하단 배치)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 150.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = onModelSelected,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800)  // 주황색
                ),
                shape = RoundedCornerShape(10.dp),     // 모서리 반지름
                modifier = Modifier
                    .width(240.dp)                    // 가로 크기
                    .height(56.dp)                    // 세로 크기
            ) {
                Text(
                    text = "상담하러 가기",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontFamily = sun_Font
                )
            }
        }
    }
}
