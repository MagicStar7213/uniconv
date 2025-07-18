package io.magicstar.uniconv

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.magicstar.uniconv.data.initConfigFile
import io.magicstar.uniconv.generated.resources.Res
import io.magicstar.uniconv.generated.resources.uniconv_logo
import io.magicstar.uniconv.ui.App
import io.magicstar.uniconv.ui.theme.UniconvTheme
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    Window(
        title = "Uniconv",
        icon = painterResource(Res.drawable.uniconv_logo),
        onCloseRequest = ::exitApplication
    ) {
        initConfigFile()
        UniconvTheme {
            App()
        }
    }
}