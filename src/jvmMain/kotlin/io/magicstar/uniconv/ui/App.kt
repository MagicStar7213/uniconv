package io.magicstar.uniconv.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.magicstar.uniconv.data.*
import io.magicstar.uniconv.generated.resources.*
import io.magicstar.uniconv.unit.convert
import io.magicstar.uniconv.unit.model.Unit
import io.magicstar.uniconv.unit.updateMagnitudes
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun App() {
    val magnitudes = listOf(
        stringResource(Res.string.length), stringResource(Res.string.weight), stringResource(Res.string.time), stringResource(Res.string.temperature),
        stringResource(Res.string.surface), stringResource(Res.string.volume), stringResource(Res.string.speed)
    )
    var magnitude by remember { mutableStateOf(dataMagnitude) }

    var enabled by remember { mutableStateOf(false) }

    var unitIndex1 by remember { mutableStateOf(0) }
    var unitIndex2 by remember { mutableStateOf(1) }

    var value by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    var reference: List<Unit> by remember { mutableStateOf(updateMagnitudes(magnitudes, magnitude)) }
    var unit1 by remember { mutableStateOf(reference.first { it.name == dataOrigin }) }
    var unit2 by remember { mutableStateOf(reference.first { it.name == dataTarget }) }

    enabled = value != ""
    reference = updateMagnitudes(magnitudes, magnitude)

    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.uniconv),
                    contentDescription = "App logo",
                    modifier = Modifier.size(90.dp)
                )
                var magnMenuExpanded by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 15.dp
                    ),
                    expanded = magnMenuExpanded,
                    onExpandedChange = { magnMenuExpanded = it }
                ) {
                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(),
                        shape = CircleShape,
                        value = magnitude,
                        onValueChange = {},
                        readOnly = true,
                        singleLine = true,
                        label = { Text(stringResource(Res.string.magnitudes)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(magnMenuExpanded) }
                    )
                    ExposedDropdownMenu(
                        expanded = magnMenuExpanded,
                        onDismissRequest = { magnMenuExpanded = false }
                    ) {
                        magnitudes.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    reference = updateMagnitudes(magnitudes, it)
                                    magnitude = it

                                    unit1 = reference[unitIndex1]
                                    unit2 = reference[unitIndex2]

                                    dataMagnitude = it
                                    dataOrigin = unit1.name
                                    dataTarget = unit2.name

                                    magnMenuExpanded = false
                                }
                            )
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        singleLine = true,
                        shape = CircleShape,
                        label = { Text(stringResource(Res.string.value)) },
                        value = value,
                        onValueChange = {
                            value = it
                        }
                    )

                    var unit1MenuExpanded by remember { mutableStateOf(false) }

                    ExposedDropdownMenuBox(
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 15.dp
                        ),
                        expanded = unit1MenuExpanded,
                        onExpandedChange = { unit1MenuExpanded = it }
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.menuAnchor(),
                            shape = CircleShape,
                            value = unit1.name,
                            onValueChange = {},
                            readOnly = true,
                            singleLine = true,
                            label = { Text(stringResource(Res.string.origin)) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(unit1MenuExpanded) }
                        )
                        ExposedDropdownMenu(
                            expanded = unit1MenuExpanded,
                            onDismissRequest = { unit1MenuExpanded = false }
                        ) {
                            reference.forEach { unit ->
                                DropdownMenuItem(
                                    text = { Text(unit.name) },
                                    onClick = {
                                        unitIndex1 = reference.indexOf(unit)
                                        unit1 = unit
                                        dataOrigin = unit1.name
                                        unit1MenuExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Text(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        text = stringResource(Res.string.to)
                    )

                    var unit2MenuExpanded by remember { mutableStateOf(false) }

                    ExposedDropdownMenuBox(
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 15.dp
                        ),
                        expanded = unit2MenuExpanded,
                        onExpandedChange = { unit2MenuExpanded = it }
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.menuAnchor(),
                            shape = CircleShape,
                            value = unit2.name,
                            onValueChange = {},
                            readOnly = true,
                            singleLine = true,
                            label = { Text(stringResource(Res.string.target)) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(unit2MenuExpanded) }
                        )
                        ExposedDropdownMenu(
                            expanded = unit2MenuExpanded,
                            onDismissRequest = { unit2MenuExpanded = false }
                        ) {
                            reference.forEach { unit ->
                                DropdownMenuItem(
                                    text = { Text(unit.name) },
                                    onClick = {
                                        unitIndex2 = reference.indexOf(unit)
                                        unit2 = unit
                                        dataTarget = unit2.name
                                        unit2MenuExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
                Button(
                    modifier = Modifier.padding(vertical = 5.dp),
                    enabled = enabled,
                    onClick = {
                        result ="${convert(value.toDouble(), unit1, unit2)} ${unit2.name}"
                    }
                ) {
                    Text(
                        text = stringResource(Res.string.convert),
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