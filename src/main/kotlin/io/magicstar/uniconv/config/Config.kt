package io.magicstar.uniconv.config

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

val json = Json { prettyPrint = true }
val languages = mapOf(
    "en" to hashMapOf(
       "magnitudes" to "Magnitudes",
        "value" to "Value",
        "origin" to "Origin",
        "target" to "Target",
        "convert" to "Convert",
        "to" to "to",
        "settings" to "Settings",
        "language" to "Language"
    ),
    "es" to hashMapOf(
        "magnitudes" to "Magnitudes",
        "value" to "Valor",
        "origin" to "Origen",
        "target" to "Destino",
        "convert" to "Convertir",
        "to" to "a",
        "settings" to "Ajustes",
        "language" to "Idioma"
    )
)

@Serializable
class Language(val lang: String)

fun init() {
    try {
        Files.createDirectory(Paths.get(System.getProperty("user.home")+"/.uniconv"))
    } catch (_: IOException) {}
    try {
        Files.createFile(Paths.get(System.getProperty("user.home")+"/.uniconv/config.json"))
    } catch (_: IOException) {}
    saveConfig("en")
}

fun getLanguage(): HashMap<String, String>? {
    val configFile = Files.readAllLines(Paths.get(System.getProperty("user.home")+"/.uniconv/config.json"))
    val jsonLang = json.decodeFromString<Language>(configFile.joinToString(""))
    return if (jsonLang.lang == "es") languages["es"]
    else languages["en"]
}

fun saveConfig(lang: String) {
    File(System.getProperty("user.home")+"/.uniconv/config.json").writeText(json.encodeToString(Language(lang)))
}