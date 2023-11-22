package adrianoqueiroz.dev.strongpass.ui.theme

import adrianoqueiroz.dev.strongpass.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val LexendDeca = FontFamily(
   Font(R.font.lexend_deca_regular),
   Font(R.font.lexend_deca_bold, FontWeight.Bold),
   Font(R.font.lexend_deca_medium, FontWeight.Medium),
   Font(R.font.lexend_deca_semi_bold, FontWeight.SemiBold),
   Font(R.font.lexend_deca_light, FontWeight.Light),
   Font(R.font.lexend_deca_extra_bold, FontWeight.ExtraBold),
   Font(R.font.lexend_deca_black, FontWeight.Black),
)

val Typography = Typography(
   bodyLarge = TextStyle(
      fontFamily = LexendDeca,
      fontWeight = FontWeight.Normal,
      fontSize = 16.sp,
      lineHeight = 24.sp,
      letterSpacing = 0.5.sp
   ),
   bodyMedium = TextStyle(
      fontFamily = LexendDeca,
      fontWeight = FontWeight.Normal,
      fontSize = 14.sp,
      lineHeight = 20.sp,
      letterSpacing = 0.25.sp
   ),
   bodySmall = TextStyle(
      fontFamily = LexendDeca,
      fontWeight = FontWeight.Normal,
      fontSize = 12.sp,
      lineHeight = 16.sp,
      letterSpacing = 0.4.sp
   ),
   titleLarge = TextStyle(
      fontFamily = LexendDeca,
      fontWeight = FontWeight.Normal,
      fontSize = 22.sp,
      lineHeight = 28.sp,
      letterSpacing = 0.sp
   ),
   titleMedium = TextStyle(
      fontFamily = LexendDeca,
      fontWeight = FontWeight.Medium,
      fontSize = 16.sp,
      lineHeight = 24.sp,
      letterSpacing = 0.15.sp
   ),
   titleSmall = TextStyle(
      fontFamily = LexendDeca,
      fontWeight = FontWeight.Medium,
      fontSize = 14.sp,
      lineHeight = 20.sp,
      letterSpacing = 0.1.sp
   ),
   labelLarge = TextStyle(
      fontFamily = LexendDeca,
      fontWeight = FontWeight.Medium,
      fontSize = 14.sp,
      lineHeight = 20.sp,
      letterSpacing = 0.1.sp
   ),
   labelMedium = TextStyle(
      fontFamily = LexendDeca,
      fontWeight = FontWeight.Medium,
      fontSize = 13.sp,
      lineHeight = 16.sp,
      letterSpacing = 0.5.sp
   ),
   labelSmall = TextStyle(
      fontFamily = LexendDeca,
      fontWeight = FontWeight.Medium,
      fontSize = 11.sp,
      lineHeight = 16.sp,
      letterSpacing = 0.5.sp
   ),
   headlineLarge = TextStyle(
      fontFamily = LexendDeca,
      fontWeight = FontWeight.Normal,
      fontSize = 32.sp,
      lineHeight = 40.sp,
      letterSpacing = 0.sp
   ),
   headlineMedium = TextStyle(
      fontFamily = LexendDeca,
      fontWeight = FontWeight.Normal,
      fontSize = 28.sp,
      lineHeight = 36.sp,
      letterSpacing = 0.sp
   ),
   headlineSmall = TextStyle(
      fontFamily = LexendDeca,
      fontWeight = FontWeight.Normal,
      fontSize = 24.sp,
      lineHeight = 32.sp,
      letterSpacing = 0.sp
   ),
   displayLarge = TextStyle(
      fontFamily = LexendDeca,
      fontWeight = FontWeight.Normal,
      fontSize = 57.sp,
      lineHeight = 64.sp,
      letterSpacing = -(0.25.sp)
   ),
   displayMedium = TextStyle(
      fontFamily = LexendDeca,
      fontWeight = FontWeight.Normal,
      fontSize = 45.sp,
      lineHeight = 52.sp,
      letterSpacing = 0.sp
   ),
   displaySmall = TextStyle(
      fontFamily = LexendDeca,
      fontWeight = FontWeight.Normal,
      fontSize = 36.sp,
      lineHeight = 44.sp,
      letterSpacing = 0.sp
   ),
)