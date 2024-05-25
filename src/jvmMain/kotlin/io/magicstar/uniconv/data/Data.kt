package io.magicstar.uniconv.data

import androidx.compose.runtime.Composable
import io.magicstar.uniconv.generated.resources.Res
import io.magicstar.uniconv.generated.resources.length
import io.magicstar.uniconv.unit.model.lengthUnits
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import java.io.File

val configFile = File("${System.getProperty("user.home")}/.uniconv/config.json")
@OptIn(ExperimentalResourceApi::class)
@Composable
fun initConfigFile() {
    if (!configFile.exists()) {
        configFile.parentFile.mkdirs()
        configFile.createNewFile()
    }
    configFile.writeText(json.encodeToString(Config(stringResource(Res.string.length), lengthUnits[0].name, lengthUnits[1].name)))
}

val json = Json { prettyPrint = true }

@Serializable
data class Config(val magnitude: String, val origin: String, val target: String)
var config: Config
    get() = json.decodeFromString(configFile.readText())
    set(value) = configFile.writeText(json.encodeToString(value))

var dataMagnitude: String
    get() {
        return config.magnitude
    }
    set(value) {
        config = config.copy(magnitude = value)
    }

var dataOrigin: String
    get() {
        return config.origin
    }
    set(value) {
        config = config.copy(origin = value)
    }

var dataTarget: String
    get() {
        return config.target
    }
    set(value) {
        config = config.copy(target = value)
    }