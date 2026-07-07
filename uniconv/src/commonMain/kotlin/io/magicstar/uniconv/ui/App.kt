package io.magicstar.uniconv.ui

import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import io.magicstar.uniconv.data.SettingsRepository
import io.magicstar.uniconv.generated.resources.Res
import io.magicstar.uniconv.generated.resources.convert
import io.magicstar.uniconv.generated.resources.magnitudes
import io.magicstar.uniconv.generated.resources.origin
import io.magicstar.uniconv.generated.resources.uniconv_text
import io.magicstar.uniconv.generated.resources.uniconv_text_dark
import io.magicstar.uniconv.generated.resources.value
import io.magicstar.uniconv.unit.convert
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@RequiresApi(9)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(repo: SettingsRepository) {
    val viewModel: UniconvViewModel = viewModel(factory = remember { UniconvViewModelFactory(repo) })
    val state by viewModel.state.collectAsState()

    var enabled by remember { mutableStateOf(false) }

    var value by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    var unit1Index by remember { mutableIntStateOf(state.reference!!.indexOf(state.unit1)) }
    var unit2Index by remember { mutableIntStateOf(state.reference!!.indexOf(state.unit2)) }

    enabled = value != ""

    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(if (isSystemInDarkTheme()) Res.drawable.uniconv_text_dark else Res.drawable.uniconv_text),
                    contentDescription = "App logo",
                    modifier = Modifier.size(90.dp)
                )
                var magnMenuExpanded by remember { mutableStateOf(false) }

                val setMagnitudeDefault = stringResource(state.magnitude!!.name)
                var magnTextFieldValue by remember { mutableStateOf(setMagnitudeDefault) }

                ExposedDropdownMenuBox(
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 15.dp
                    ),
                    expanded = magnMenuExpanded,
                    onExpandedChange = {
                        magnMenuExpanded = it
                        if (magnMenuExpanded) magnTextFieldValue = ""
                    }
                ) {
                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true),
                        shape = CircleShape,
                        value = magnTextFieldValue,
                        onValueChange = {
                            magnTextFieldValue = it
                            magnMenuExpanded = true
                        },
                        readOnly = false,
                        singleLine = true,
                        label = { Text(stringResource(Res.string.magnitudes)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(magnMenuExpanded) }
                    )
                    if (!magnMenuExpanded) magnTextFieldValue = stringResource(state.magnitude!!.name)
                    val tempMagnitudes = viewModel.magnitudesList.filter { stringResource(it.name).normalize().contains(magnTextFieldValue.normalize(), true) }
                    if (tempMagnitudes.isNotEmpty())
                        DropdownMenu(
                            modifier = Modifier.exposedDropdownSize(false),
                            properties = PopupProperties(focusable = true),
                            scrollState = rememberScrollState(),
                            expanded = magnMenuExpanded,
                            onDismissRequest = { magnMenuExpanded = false }
                        ) {
                            tempMagnitudes.forEach {
                                DropdownMenuItem(
                                    text = { Text(stringResource(it.name)) },
                                    onClick = {
                                        viewModel.onIntent(UniconvIntent.ChangeMagnitude(it))
                                        magnMenuExpanded = false
                                    }
                                )
                            }
                        }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val focusManager = LocalFocusManager.current
                    OutlinedTextField(
                        modifier = Modifier
                            .weight(.5f)
                            .padding(
                                start = 50.dp,
                                top = 15.dp,
                                bottom = 15.dp,
                                end = 4.dp
                            )
                            .onKeyEvent {
                                if (it.key == Key.Enter) {
                                    result = "${convert(value.toDouble(), state.unit1!!, state.unit2!!)} ${state.unit2!!.abbreviation}"
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

                    val setOriginDefault = "${stringResource(state.unit1!!.name)} (${state.unit1!!.abbreviation})"
                    var textFieldValue1 by remember { mutableStateOf(setOriginDefault) }
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
                        onExpandedChange = {
                            originMenuExpanded = it
                            if (originMenuExpanded) textFieldValue1 = ""
                        }
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true),
                            shape = CircleShape,
                            value = textFieldValue1,
                            onValueChange = {
                                textFieldValue1 = it
                                originMenuExpanded = true
                            },
                            readOnly = false,
                            singleLine = true,
                            label = { Text(stringResource(Res.string.origin)) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(originMenuExpanded) }
                        )
                        if (!originMenuExpanded) textFieldValue1 = setOriginDefault
                        val tempReference = state.reference!!.filter { "${stringResource(it.name)} (${it.abbreviation})".normalize().contains(textFieldValue1.normalize(), true) }
                        if (tempReference.isNotEmpty())
                            DropdownMenu(
                                modifier = Modifier.exposedDropdownSize(false),
                                properties = PopupProperties(focusable = true),
                                scrollState = rememberScrollState(),
                                expanded = originMenuExpanded,
                                onDismissRequest = { originMenuExpanded = false }
                            ) {
                                tempReference.forEach { unit ->
                                    DropdownMenuItem(
                                        text = { Text("${stringResource(unit.name)} (${unit.abbreviation})") },
                                        onClick = {
                                            unit1Index = state.reference!!.indexOf(unit)
                                            viewModel.onIntent(UniconvIntent.ChangeUnit(unit, 1))
                                            originMenuExpanded = false
                                        }
                                    )
                                }
                            }

                    }

                    var targetMenuExpanded by remember { mutableStateOf(false) }

                    val setTargetDefault = "${stringResource(state.unit2!!.name)} (${state.unit2!!.abbreviation})"
                    var textFieldValue2 by remember { mutableStateOf(setTargetDefault) }
                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .weight(.3f)
                            .padding(
                                start = 10.dp,
                                end = 4.dp,
                                top = 15.dp,
                                bottom = 15.dp
                            ),
                        expanded = targetMenuExpanded,
                        onExpandedChange = {
                            targetMenuExpanded = it
                            if (targetMenuExpanded) textFieldValue2 = ""
                        }
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true),
                            shape = CircleShape,
                            value = textFieldValue2,
                            onValueChange = {
                                textFieldValue2 = it
                                targetMenuExpanded = true
                            },
                            readOnly = false,
                            singleLine = true,
                            label = { Text(stringResource(Res.string.origin)) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(targetMenuExpanded) }
                        )
                        if (!targetMenuExpanded) textFieldValue2 = setTargetDefault
                        val tempReference = state.reference!!.filter { "${stringResource(it.name)} (${it.abbreviation})".normalize().contains(textFieldValue2.normalize(), true) }
                        if (tempReference.isNotEmpty())
                            DropdownMenu(
                                modifier = Modifier.exposedDropdownSize(false),
                                properties = PopupProperties(focusable = true),
                                scrollState = rememberScrollState(),
                                expanded = targetMenuExpanded,
                                onDismissRequest = { targetMenuExpanded = false }
                            ) {
                                tempReference.forEach { unit ->
                                    DropdownMenuItem(
                                        text = { Text("${stringResource(unit.name)} (${unit.abbreviation})") },
                                        onClick = {
                                            unit2Index = state.reference!!.indexOf(unit)
                                            viewModel.onIntent(UniconvIntent.ChangeUnit(unit, 2))
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
                        result ="${convert(value.toDouble(), state.unit1!!, state.unit2!!)} ${state.unit2!!.abbreviation}"
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
}