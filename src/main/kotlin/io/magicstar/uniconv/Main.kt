package io.magicstar.uniconv

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.magicstar.uniconv.ui.app

fun main() = application {
    Window(
        title = "Uniconv",
        icon = painterResource("uniconv-logo.svg"),
        onCloseRequest = ::exitApplication
    ) {
        app()
    }
}