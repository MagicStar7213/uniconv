package io.magicstar.uniconv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import io.magicstar.uniconv.data.initConfig
import io.magicstar.uniconv.ui.App
import io.magicstar.uniconv.ui.theme.UniconvTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            initConfig(this@MainActivity.applicationContext)
        }

        setContent {
            UniconvTheme {
                App(this.applicationContext)
            }
        }
    }
}