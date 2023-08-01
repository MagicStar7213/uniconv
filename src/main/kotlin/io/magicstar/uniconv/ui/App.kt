package io.magicstar.uniconv.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.material.ExposedDropDownMenu
import androidx.compose.material.OutlinedExposedDropDownMenu
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*

import io.magicstar.uniconv.unit.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun app() {
    val magnitudes = setOf(
        "Length", "Weight", "Time", "Temperature", "Surface", "Volume", "Speed", "Electric Current"
    )
    var magnitude by remember { mutableStateOf(magnitudes.elementAt(0)) }

    var enabled by remember { mutableStateOf(false) }

    var selectedIndexMagn by remember { mutableStateOf(0) }
    var selectedIndex1 by remember { mutableStateOf(0) }
    var selectedIndex2 by remember { mutableStateOf(1) }

    var value by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    var reference by remember { mutableStateOf(length_reference) }
    var unit1 by remember { mutableStateOf(reference.keys.elementAt(0)) }
    var unit2 by remember { mutableStateOf(reference.keys.elementAt(1)) }

    enabled = value != ""

    reference = updateMagnitudes(magnitudes, magnitude)

    MaterialTheme {
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
            ExposedDropDownMenu(
                modifier = Modifier.padding(
                    horizontal = 8.dp,
                    vertical = 15.dp
                ),
                values = magnitudes,
                label = { Text("Magnitudes") },
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
                    label = { Text("Value") },
                    value = value,
                    onValueChange = {
                        value = it
                    }
                )

                OutlinedExposedDropDownMenu(
                    values = reference.keys,
                    label = { Text("Origin") },
                    selectedIndex = selectedIndex1,
                    onChange = {
                        unit1 = reference.keys.elementAt(it)
                        selectedIndex1 = it
                    }
                )
                Text(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = "to"
                )
                OutlinedExposedDropDownMenu(
                    values = reference.keys,
                    label = { Text("Target") },
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
                    text = "Convert",
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
        else -> length_reference
    }
}

@Preview
@Composable
fun mainPreview() {
    app()
}