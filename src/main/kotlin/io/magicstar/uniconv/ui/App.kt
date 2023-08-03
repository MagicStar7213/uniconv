package io.magicstar.uniconv.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.material.OutlinedExposedDropDownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.*

import io.magicstar.uniconv.unit.*
import io.magicstar.uniconv.config.*
import java.nio.file.Paths
import kotlin.io.path.exists

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun app() {
    var language by remember { mutableStateOf(getLanguage()) }
    var magnitudes by remember {
        if (language == languages["es"])
            mutableStateOf(setOf(
                "Longitud", "Masa", "Tiempo", "Temperatura",
                "Superficie", "Volumen", "Velocidad", "Corriente El\u00e9ctrica", "Potencia"
            ))
        else
            mutableStateOf(setOf(
                "Length", "Weight", "Time", "Temperature",
                "Surface", "Volume", "Speed", "Electric Current", "Power"
            ))
    }
    var langsAvailable by remember {
        if (language == languages["es"]) mutableStateOf(setOf("Ingl\u00e9s", "Espa\u00f1ol"))
        else mutableStateOf(setOf("English", "Spanish"))
    }
    var magnitude by remember { mutableStateOf(magnitudes.elementAt(0)) }

    var enabled by remember { mutableStateOf(false) }

    var selectedIndexMagn by remember { mutableStateOf(0) }
    var selectedIndex1 by remember { mutableStateOf(0) }
    var selectedIndex2 by remember { mutableStateOf(1) }
    var selectedIndexLang by remember {
        if (language == languages["es"]) mutableStateOf(1)
        else mutableStateOf(0)
    }

    var value by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    var reference by remember { mutableStateOf(length_reference) }
    var unit1 by remember { mutableStateOf(reference.keys.elementAt(0)) }
    var unit2 by remember { mutableStateOf(reference.keys.elementAt(1)) }

    var dialogVisible by remember { mutableStateOf(false) }

    if (!Paths.get(System.getProperty("user.home")+"/.uniconv/config.json").exists())
        init()

    enabled = value != ""
    reference = updateMagnitudes(magnitudes, magnitude)

    MaterialTheme {
        if (dialogVisible)
            AlertDialog(
                onDismissRequest = { dialogVisible = false },
                shape = RoundedCornerShape(16.dp),
                buttons = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 14.dp),
                                text = language!!["settings"]!!,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            IconButton(
                                modifier = Modifier.padding(horizontal = 4.dp),
                                onClick = { dialogVisible = false }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Cancel,
                                    contentDescription = "Cancel",
                                    tint = Color.DarkGray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedExposedDropDownMenu(
                            modifier = Modifier.padding(
                                horizontal = 14.dp,
                                vertical = 14.dp
                            ),
                            shape = RoundedCornerShape(20.dp),
                            values = langsAvailable,
                            label = { Text(language!!["language"]!!) },
                            selectedIndex = selectedIndexLang,
                            onChange = {
                                selectedIndexLang = it
                                when (selectedIndexLang) {
                                    0 -> {
                                        saveConfig("en")
                                        langsAvailable = setOf("English", "Spanish")
                                        magnitudes = setOf(
                                            "Length", "Weight", "Time", "Temperature",
                                            "Surface", "Volume", "Speed", "Electric Current", "Power"
                                        )
                                    }
                                    1 -> {
                                        saveConfig("es")
                                        langsAvailable = setOf("Ingl\u00e9s", "Espa\u00f1ol")
                                        magnitudes = setOf(
                                            "Longitud", "Masa", "Tiempo", "Temperatura",
                                            "Superficie", "Volumen", "Velocidad", "Corriente El\u00e9ctrica", "Potencia"
                                        )
                                    }
                                }
                                language = getLanguage()
                            }
                        )
                    }
                }
            )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = { dialogVisible = true }
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings",
                    tint = Color.DarkGray
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource("uniconv-logo.svg"),
                    contentDescription = "App logo",
                    modifier = Modifier.size(90.dp)
                )
                Text(
                    text = "Uniconv",
                    fontSize = 30.sp
                )
                OutlinedExposedDropDownMenu(
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 15.dp
                    ),
                    shape = RoundedCornerShape(20.dp),
                    values = magnitudes,
                    label = { Text(language?.get("magnitudes")!!) },
                    selectedIndex = selectedIndexMagn,
                    onChange = {
                        magnitude = magnitudes.elementAt(it)
                        selectedIndexMagn = it

                        reference = updateMagnitudes(magnitudes, magnitude)

                        unit1 = reference.keys.elementAt(selectedIndex1)
                        unit2 = reference.keys.elementAt(selectedIndex2)
                    }
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        shape = CircleShape,
                        label = { Text(language?.get("value")!!) },
                        value = value,
                        onValueChange = {
                            value = it
                        }
                    )

                    OutlinedExposedDropDownMenu(
                        shape = RoundedCornerShape(20.dp),
                        values = reference.keys,
                        label = { Text(language?.get("origin")!!) },
                        selectedIndex = selectedIndex1,
                        onChange = {
                            unit1 = reference.keys.elementAt(it)
                            selectedIndex1 = it
                        }
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        text = language?.get("to")!!
                    )
                    OutlinedExposedDropDownMenu(
                        shape = RoundedCornerShape(20.dp),
                        values = reference.keys,
                        label = { Text(language!!["target"]!!) },
                        selectedIndex = selectedIndex2,
                        onChange = {
                            unit2 = reference.keys.elementAt(it)
                            selectedIndex2 = it
                        }
                    )
                }
                Button(
                    modifier = Modifier.padding(vertical = 5.dp),
                    enabled = enabled,
                    onClick = {
                        result = if (magnitude == magnitudes.elementAt(3))
                            "${heatConvert(value.toDouble(), unit1, unit2)} $unit2"
                        else
                            "${conversion(value.toDouble(), reference[unit1]!!, reference[unit2]!!)} $unit2"
                    }
                ) {
                    Text(
                        text = language?.get("convert")!!,
                        fontSize = 17.5.sp
                    )
                }
                SelectionContainer {
                    Text(
                        text = result,
                        fontSize = 25.sp
                    )
                }
            }
        }
    }
}

fun updateMagnitudes(magnitudes: Set<String>, magnitude: String): HashMap<String, Double> {
    return when (magnitude) {
        magnitudes.elementAt(0) -> length_reference
        magnitudes.elementAt(1) -> weight_reference
        magnitudes.elementAt(2) -> time_reference
        magnitudes.elementAt(3) -> heat_reference
        magnitudes.elementAt(4) -> surface_reference
        magnitudes.elementAt(5) -> volume_reference
        magnitudes.elementAt(6) -> speed_reference
        magnitudes.elementAt(7) -> elec_current_reference
        magnitudes.elementAt(8) -> power_reference
        else -> length_reference
    }
}