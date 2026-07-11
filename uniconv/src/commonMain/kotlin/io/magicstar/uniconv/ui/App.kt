package io.magicstar.uniconv.ui

import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import io.magicstar.uniconv.data.SettingsRepository
import io.magicstar.uniconv.generated.resources.Res
import io.magicstar.uniconv.generated.resources.magnitudes
import io.magicstar.uniconv.generated.resources.uniconv_text
import io.magicstar.uniconv.generated.resources.uniconv_text_dark
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import java.math.BigDecimal

@RequiresApi(9)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(repo: SettingsRepository) {
    val viewModel: UniconvViewModel = viewModel(factory = remember { UniconvViewModelFactory(repo) })
    val state by viewModel.state.collectAsState()

    val measurer = rememberTextMeasurer()
    val unitSelectorWidth by remember {
        mutableIntStateOf(
            measurer.measure(state.reference!!.maxBy { it.abbreviation.length }.abbreviation).size.width
        )
    }

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

                UnitGroup(
                    state,
                    unitType = 1,
                    unitSelectorWidth,
                    initialUnitText = { state.unit1!!.abbreviation },
                    onValueChange = {
                        viewModel.onIntent(UniconvIntent.UpdateTextFieldValue(it,1))
                        viewModel.onIntent(UniconvIntent.Calculate(
                            Triple(state.unit1!!, state.unit2!!, 2),
                            if (it != "") BigDecimal(it) else BigDecimal("0"))
                        )
                    },
                    onClick = { viewModel.onIntent(UniconvIntent.ChangeUnit(it, 1)) }
                )

                UnitGroup(
                    state,
                    unitType = 2,
                    unitSelectorWidth,
                    initialUnitText = { state.unit2!!.abbreviation },
                    onValueChange = {
                        viewModel.onIntent(UniconvIntent.UpdateTextFieldValue(it,2))
                        viewModel.onIntent(UniconvIntent.Calculate(
                            Triple(state.unit1!!, state.unit2!!, 1),
                            if (it != "") BigDecimal(it) else BigDecimal("0"))
                        )
                    },
                    onClick = { viewModel.onIntent(UniconvIntent.ChangeUnit(it, 2)) }
                )
            }
        }
    }
}