package io.magicstar.uniconv.ui

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import io.magicstar.uniconv.data.getKey
import io.magicstar.uniconv.data.magnitudeKey
import io.magicstar.uniconv.data.originKey
import io.magicstar.uniconv.data.saveKey
import io.magicstar.uniconv.data.targetKey
import io.magicstar.uniconv.generated.resources.Res
import io.magicstar.uniconv.generated.resources.convert
import io.magicstar.uniconv.generated.resources.length
import io.magicstar.uniconv.generated.resources.magnitudes
import io.magicstar.uniconv.generated.resources.origin
import io.magicstar.uniconv.generated.resources.speed
import io.magicstar.uniconv.generated.resources.surface
import io.magicstar.uniconv.generated.resources.swap
import io.magicstar.uniconv.generated.resources.target
import io.magicstar.uniconv.generated.resources.temperature
import io.magicstar.uniconv.generated.resources.time
import io.magicstar.uniconv.generated.resources.uniconv
import io.magicstar.uniconv.generated.resources.value
import io.magicstar.uniconv.generated.resources.volume
import io.magicstar.uniconv.generated.resources.weight
import io.magicstar.uniconv.unit.convert
import io.magicstar.uniconv.unit.model.Unit
import io.magicstar.uniconv.unit.model.lengthUnits
import io.magicstar.uniconv.unit.model.speedUnits
import io.magicstar.uniconv.unit.model.surfaceUnits
import io.magicstar.uniconv.unit.model.temperatureUnits
import io.magicstar.uniconv.unit.model.timeUnits
import io.magicstar.uniconv.unit.model.volumeUnits
import io.magicstar.uniconv.unit.model.weightUnits
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

    GetDropdownWidth(
        view = {
            val widthList = mutableListOf<String>()
            for (ref in listOf(lengthUnits, weightUnits, timeUnits, temperatureUnits, surfaceUnits, volumeUnits, speedUnits)) {
                val allStrings = ref.map { "${stringResource(it.name)} (${it.abbreviation})" }
                widthList.add(allStrings.maxBy { it.length })
            }
            val maxString = widthList.maxBy { it.length }
            Text(maxString)
        }
    ) { dropdownWidth ->
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

                                originIndex = if (originIndex > reference.lastIndex) {
                                    if (originIndex > targetIndex) reference.lastIndex else reference.lastIndex - 1
                                } else originIndex

                                targetIndex = if (targetIndex > reference.lastIndex) {
                                    if (targetIndex > originIndex) reference.lastIndex else reference.lastIndex - 1
                                } else targetIndex

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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(onGo = {
                    result ="${convert(value.toDouble(), origin, target)} ${target.abbreviation}"
                    keyboardController?.hide()
                })
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                var originMenuExpanded by remember { mutableStateOf(false) }

                val setOriginDefault = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE)
                    "${stringResource(origin.name)} (${origin.abbreviation})"
                else origin.abbreviation
                var textFieldValue1 by remember { mutableStateOf(setOriginDefault) }
                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .weight(.3f)
                        .padding(
                            start = 50.dp,
                            end = 4.dp,
                            top = 15.dp,
                            bottom = 15.dp
                        ),
                    expanded = originMenuExpanded,
                    onExpandedChange = {
                        originMenuExpanded = it
                        if (originMenuExpanded) textFieldValue1 = ""
                    }
                ) {
                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
                        shape = CircleShape,
                        value = textFieldValue1,
                        onValueChange = {
                            originMenuExpanded = true
                            textFieldValue1 = it
                        },
                        readOnly = false,
                        singleLine = true,
                        label = { Text(stringResource(Res.string.origin)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(originMenuExpanded) }
                    )
                    if (!originMenuExpanded) textFieldValue1 = setOriginDefault
                    val tempReference = reference.filter { "${stringResource(it.name)} (${it.abbreviation})".normalize().contains(textFieldValue1.normalize(), true) }
                    if (tempReference.isNotEmpty())
                        DropdownMenu(
                            modifier = Modifier
                                .exposedDropdownSize(false)
                                .width(dropdownWidth),
                            properties = PopupProperties(focusable = false, clippingEnabled = false, dismissOnClickOutside = false),
                            scrollState = rememberScrollState(),
                            expanded = originMenuExpanded,
                            onDismissRequest = { originMenuExpanded = false }
                        ) {
                            tempReference.forEach { unit ->
                            DropdownMenuItem(
                                text = { Text(
                                    text = "${stringResource(unit.name)} (${unit.abbreviation})",
                                ) },
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

                val setTargetDefault = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE)
                    "${stringResource(target.name)} (${target.abbreviation})"
                else target.abbreviation
                var textFieldValue2 by remember { mutableStateOf(setTargetDefault) }
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
                    onExpandedChange = {
                        targetMenuExpanded = it
                        if (targetMenuExpanded) textFieldValue2 = ""
                    }
                ) {
                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
                        shape = CircleShape,
                        value = textFieldValue2,
                        onValueChange = {
                            targetMenuExpanded = true
                            textFieldValue2 = it
                        },
                        readOnly = false,
                        singleLine = true,
                        label = { Text(stringResource(Res.string.target)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(targetMenuExpanded) }
                    )
                    if (!targetMenuExpanded) textFieldValue2 = setTargetDefault
                    val tempReference = reference.filter { "${stringResource(it.name)} (${it.abbreviation})".normalize().contains(textFieldValue2.normalize(), true) }
                    if (tempReference.isNotEmpty())
                        DropdownMenu(
                            modifier = Modifier
                                .exposedDropdownSize(false)
                                .width(dropdownWidth),
                            properties = PopupProperties(focusable = false, clippingEnabled = false, dismissOnClickOutside = false),
                            scrollState = rememberScrollState(),
                            expanded = targetMenuExpanded,
                            onDismissRequest = { targetMenuExpanded = false }
                        ) {
                            tempReference.forEach { unit ->
                            DropdownMenuItem(
                                text = { Text(
                                    text = "${stringResource(unit.name)} (${unit.abbreviation})",
                                ) },
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
                    result ="${convert(value.toDouble(), origin, target)} ${target.abbreviation}"
                }
            ) {
                Text(
                    text = stringResource(Res.string.convert),
                    fontSize = 17.5.sp
                )
            }
            SelectionContainer {
                Text(
                    modifier = Modifier.padding(horizontal = 50.dp),
                    text = result,
                    fontSize = 25.sp
                )
            }
        }
    }



}
