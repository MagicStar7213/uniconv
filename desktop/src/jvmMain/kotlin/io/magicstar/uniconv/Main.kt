package io.magicstar.uniconv

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.magicstar.uniconv.data.SettingsDataRepository
import io.magicstar.uniconv.data.createDataStore
import io.magicstar.uniconv.generated.resources.Res
import io.magicstar.uniconv.generated.resources.uniconv
import io.magicstar.uniconv.ui.App
import io.magicstar.uniconv.ui.theme.UniconvTheme
import org.jetbrains.compose.resources.painterResource

fun main() {
    val dataStore = createDataStore()
    application {
        Window(
            title = "Uniconv",
            icon = painterResource(Res.drawable.uniconv),
            onCloseRequest = ::exitApplication
        ) {
            UniconvTheme {
                App(SettingsDataRepository(dataStore = dataStore))
            }
        }
    }
}