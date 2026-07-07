package io.magicstar.uniconv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.magicstar.uniconv.data.SettingsDataRepository
import io.magicstar.uniconv.data.createDataStore
import io.magicstar.uniconv.ui.App
import io.magicstar.uniconv.ui.theme.UniconvTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UniconvTheme {
                App(SettingsDataRepository(dataStore = createDataStore(this.applicationContext)))
            }
        }
    }
}