package com.example.hopecard.ui.forgot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// Brand colours (shared with all pages)
private val BgSheet   = Color(0xFFF5CACA)  // matches bg_sheet.xml #F5CACA
private val DarkRed   = Color(0xFF5B1717)
// Button gradient colours – match bg_primary_button_gradient.xml exactly
private val BtnStart  = Color(0xFFD97373)  // #D97373 top
private val BtnEnd    = Color(0xFFB94F4F)  // #B94F4F bottom
private val HintGray  = Color(0xFF9E9E9E)
private val InputBg   = Color(0xFFFFFFFF)
private val TextColor = Color(0xFF333333)

/**
 * Compose re-implementation of activity_reset_password.xml.
 *
 * Layout:
 *  - 260 dp carousel header (photo + pink #66F5CACA overlay + H logo)
 *  - Rounded-top bg_sheet panel overlapping header by 42 dp
 *  - "Forgot Password?" / "Re-enter New Password" titles
 *  - White 48 dp input card (key icon + BasicTextField – NO clipping)
 *  - Gradient "Continue" pill (160 dp × 46 dp)
 *  - Footer with divider lines + "Sign up here!" link
 */
@Composable
fun ResetPasswordComposeScreen(
    carouselImages: List<Int>,
    onContinue: (newPassword: String) -> Unit,
    onSignUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // ── Carousel header (top 260 dp) ──────────────────────────────────
        CarouselHeader(
            images = carouselImages,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .align(Alignment.TopCenter)
        )

        // ── Sheet: starts at 260-42 = 218 dp from top, fills remainder ───
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 218.dp)
                .clip(RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp))
                .background(BgSheet)
                .padding(horizontal = 26.dp)
                .padding(top = 26.dp, bottom = 22.dp)
        ) {
            // Title
            Text(
                text = "Forgot Password?",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = DarkRed,
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Sub-title
            Text(
                text = "Re-enter New Password",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = DarkRed,
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ── Input card ────────────────────────────────────────────────
            PasswordInputCard(onContinue = onContinue)

            Spacer(modifier = Modifier.weight(1f))

            // ── Footer ───────────────────────────────────────────────────
            SignUpFooter(onSignUp = onSignUp)
        }
    }
}

// ── Password card + Continue button ──────────────────────────────────────────

@Composable
private fun PasswordInputCard(onContinue: (String) -> Unit) {
    var password by remember { mutableStateOf("") }

    Column {
        // White elevated card – exactly mirrors CardView in XML
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(10.dp)),
            shape = RoundedCornerShape(10.dp),
            color = InputBg,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Key icon (tinted grey like XML app:tint="#9E9E9E")
                Image(
                    painter = painterResource(id = com.example.hopecard.R.drawable.ic_key),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(HintGray),
                    modifier = Modifier.size(18.dp),
                )

                Spacer(modifier = Modifier.width(10.dp))

                // BasicTextField + manual placeholder so we have zero decoration padding
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (password.isEmpty()) {
                        Text(
                            text = "Enter New Password",
                            color = HintGray,
                            fontSize = 14.sp,
                        )
                    }
                    BasicTextField(
                        value = password,
                        onValueChange = { password = it },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        textStyle = TextStyle(
                            color = TextColor,
                            fontSize = 14.sp,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Gradient pill button (matches bg_primary_button_gradient + CardView in XML)
        val pillShape = RoundedCornerShape(23.dp)
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(160.dp)
                .height(46.dp)
                .shadow(elevation = 10.dp, shape = pillShape)
                .clip(pillShape)
                .background(Brush.verticalGradient(listOf(BtnStart, BtnEnd)))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    val trimmed = password.trim()
                    if (trimmed.isNotEmpty()) onContinue(trimmed)
                },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Continue",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

// ── Footer ────────────────────────────────────────────────────────────────────

@Composable
private fun SignUpFooter(onSignUp: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(Modifier.weight(1f).height(1.dp).background(Color(0xAA5B1717)))

        Text(
            text = "Don't have an account?",
            fontSize = 11.sp,
            color = DarkRed,
            modifier = Modifier.padding(horizontal = 6.dp),
        )

        Text(
            text = "Sign up here!",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = DarkRed,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onSignUp,
                )
                .padding(horizontal = 2.dp),
        )

        Box(Modifier.weight(1f).height(1.dp).background(Color(0xAA5B1717)))
    }
}

// ── Carousel header ───────────────────────────────────────────────────────────

@Composable
private fun CarouselHeader(
    images: List<Int>,
    modifier: Modifier = Modifier,
) {
    if (images.isEmpty()) {
        Box(modifier = modifier.background(Color.Black))
        return
    }

    var index by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3_000)
            index = (index + 1) % images.size
        }
    }

    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = images[index]),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        // Pink overlay #66F5CACA
        Box(Modifier.fillMaxSize().background(Color(0x66F5CACA)))
        // H logo bottom-start
        Image(
            painter = painterResource(id = com.example.hopecard.R.drawable.logo_h),
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, bottom = 60.dp)
                .size(60.dp),
        )
    }
}
