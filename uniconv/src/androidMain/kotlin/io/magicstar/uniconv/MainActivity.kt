package io.magicstar.uniconv

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import io.magicstar.uniconv.data.initConfig
import io.magicstar.uniconv.generated.resources.Res
import io.magicstar.uniconv.generated.resources.length
import io.magicstar.uniconv.ui.App
import io.magicstar.uniconv.ui.theme.UniconvTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val defaultMagnitude = stringResource(Res.string.length)
            lifecycleScope.launch(Dispatchers.IO) {
                initConfig(this@MainActivity.applicationContext, defaultMagnitude)
            }
            UniconvTheme {
                App(this.applicationContext)
            }
        }
    }
}