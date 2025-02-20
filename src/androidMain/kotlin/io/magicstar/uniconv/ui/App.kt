package io.magicstar.uniconv.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.magicstar.uniconv.data.getKey
import io.magicstar.uniconv.data.magnitudeKey
import io.magicstar.uniconv.data.originKey
import io.magicstar.uniconv.data.saveKey
import io.magicstar.uniconv.data.targetKey
import io.magicstar.uniconv.generated.resources.*
import io.magicstar.uniconv.unit.convert
import io.magicstar.uniconv.unit.model.Unit
import io.magicstar.uniconv.unit.updateMagnitudes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(context: Context) {
    val magnitudes = listOf(
        stringResource(Res.string.length), stringResource(Res.string.weight), stringResource(Res.string.time), stringResource(Res.string.temperature),
        stringResource(Res.string.surface), stringResource(Res.string.volume), stringResource(Res.string.speed)
    )
    var magnitude by remember { runBlocking { mutableStateOf(getKey(context, magnitudeKey)) } }

    var enabled by remember { mutableStateOf(false) }

    var value by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    var reference: List<Unit> by remember { mutableStateOf(updateMagnitudes(magnitudes, magnitude)) }
    var origin by remember { mutableStateOf(reference.first { it.abbreviation == runBlocking(Dispatchers.IO) { getKey(context, originKey) } }) }
    var target by remember { mutableStateOf(reference.first { it.abbreviation == runBlocking(Dispatchers.IO) { getKey(context, targetKey) } }) }

    var originIndex by remember { mutableIntStateOf(reference.indexOf(reference.first { it.abbreviation == runBlocking(Dispatchers.IO) { getKey(context, originKey) } })) }
    var targetIndex by remember { mutableIntStateOf(reference.indexOf(reference.first { it.abbreviation == runBlocking(Dispatchers.IO) { getKey(context, targetKey) } })) }

    enabled = value != ""
    reference = updateMagnitudes(magnitudes, magnitude)

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
                vertical = 8.dp
            ),
            expanded = magnMenuExpanded,
            onExpandedChange = { magnMenuExpanded = it }
        ) {
            OutlinedTextField(
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
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

                            origin = reference[originIndex]
                            target = reference[targetIndex]

                            CoroutineScope(Dispatchers.IO).launch {
                                saveKey(context, magnitudeKey, magnitude)
                                saveKey(context, originKey, origin.abbreviation)
                                saveKey(context, targetKey, target.abbreviation)
                            }

                            magnMenuExpanded = false
                        }
                    )
                }
            }
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        OutlinedTextField(
            modifier = Modifier.padding(8.dp),
            singleLine = true,
            shape = CircleShape,
            label = { Text(stringResource(Res.string.value)) },
            value = value,
            onValueChange = {
                value = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Go),
            keyboardActions = KeyboardActions(onGo = {
                result ="${convert(value.toDouble(), origin, target)} ${target.name}"
                keyboardController?.hide()
            })
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val focusManager = LocalFocusManager.current
            OutlinedTextField(
                modifier = Modifier
                    .padding(
                        start = 50.dp,
                        top = 15.dp,
                        bottom = 15.dp,
                        end = 4.dp
                    )
                    .onKeyEvent {
                        if (it.key == Key.Enter) {
                            result = "${convert(value.toDouble(), origin, target)} ${target.name}"
                            focusManager.clearFocus()
                            true
                        } else false
                    },
                singleLine = true,
                shape = CircleShape,
                label = { Text(stringResource(Res.string.value)) },
                value = value,
                onValueChange = {
                    value = it
                }
            )

            var originMenuExpanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                modifier = Modifier
                    .weight(.3f)
                    .padding(
                        start = 10.dp,
                        end = 4.dp,
                        top = 15.dp,
                        bottom = 15.dp
                    ),
                expanded = originMenuExpanded,
                onExpandedChange = { originMenuExpanded = it }
            ) {
                OutlinedTextField(
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
                    shape = CircleShape,
                    value = "${stringResource(origin.name)} (${origin.abbreviation})",
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    label = { Text(stringResource(Res.string.origin)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(originMenuExpanded) }
                )
                ExposedDropdownMenu(
                    expanded = originMenuExpanded,
                    onDismissRequest = { originMenuExpanded = false }
                ) {
                    reference.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text("${stringResource(unit.name)} (${unit.abbreviation})") },
                            onClick = {
                                originIndex = reference.indexOf(unit)
                                origin = unit
                                CoroutineScope(Dispatchers.IO).launch { saveKey(context, originKey, origin.abbreviation) }
                                originMenuExpanded = false
                            }
                        )
                    }
                }
            }

            IconButton(
                onClick = {
                    originIndex = targetIndex.also { targetIndex = originIndex }
                    origin = reference[originIndex]
                    target = reference[targetIndex]
                }
            )  {
                Icon(imageVector = Icons.Default.SwapHoriz, contentDescription = stringResource(Res.string.swap))
            }

            var targetMenuExpanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                modifier = Modifier
                    .weight(.3f)
                    .padding(
                        start = 4.dp,
                        top = 15.dp,
                        bottom = 15.dp,
                        end = 50.dp
                    ),
                expanded = targetMenuExpanded,
                onExpandedChange = { targetMenuExpanded = it }
            ) {
                OutlinedTextField(
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
                    shape = CircleShape,
                    value = "${stringResource(target.name)} (${target.abbreviation})",
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    label = { Text(stringResource(Res.string.target)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(targetMenuExpanded) }
                )
                ExposedDropdownMenu(
                    expanded = targetMenuExpanded,
                    onDismissRequest = { targetMenuExpanded = false }
                ) {
                    reference.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text("${stringResource(unit.name)} (${unit.abbreviation})") },
                            onClick = {
                                targetIndex = reference.indexOf(unit)
                                target = unit
                                CoroutineScope(Dispatchers.IO).launch { saveKey(context, targetKey, target.abbreviation) }
                                targetMenuExpanded = false
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
                result ="${convert(value.toDouble(), origin, target)} ${target.name}"
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