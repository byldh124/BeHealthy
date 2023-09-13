package com.moondroid.behealthy.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Blue01 = Color(0xFF144272)
val Blue02 = Color(0xFF0A2647)
val Blue03 = Color(0xFF205295)
val Blue04 = Color(0xFF2C74B3)

val Gray01 = Color(0xFF76787b)
val Gray02 = Color(0xFF4c4d4e)
val Gray03 = Color(0xFF3d3d3d)

enum class BoxColor(val color: Color) {
    Box01(Color(0xFFFFE533)),
    Box02(Color(0xFFF0D1C4)),
    Box03(Color(0xFFF17F67)),
    Box04(Color(0xFFFFC2C8)),
    Box05(Color(0xFFFFA4B0)),
    Box06(Color(0xFFE75E60)),
    Box07(Color(0xFFE25A7C)),
    Box08(Color(0xFFA1B4DA)),
    Box09(Color(0xFFBB69A4)),
    Box10(Color(0xFFA747A6)),
    Box11(Color(0xFF726FBE)),
    Box12(Color(0xFFC7E4D8)),
    Box13(Color(0xFF89BC58)),
    Box14(Color(0xFF46BB6B)),
    Box15(Color(0xFF32927A)),
    Box16(Color(0xFFDEEAF1)),
    Box17(Color(0xFFC5DEEF)),
    Box18(Color(0xFF92D6DE)),
    Box19(Color(0xFF49BDB4)),
    Box20(Color(0xFF00C0E7)),
    Box21(Color(0xFF4184CD)),
    Box22(Color(0xFF0083D6)),
    Box23(Color(0xFF307DF8)),
    Box24(Color(0xFF75959B)),
    Box25(Color(0xFF5386A1)),
    Box26(Color(0xFF1160AC)),
    Box27(Color(0xFF0A4C82)),
    Box28(Color(0xFF55678E)),
    Box29(Color(0xFF5E6774));

    companion object {
        fun getRandom(): BoxColor {
            return BoxColor.values().toList().shuffled().first()
        }
    }
}



