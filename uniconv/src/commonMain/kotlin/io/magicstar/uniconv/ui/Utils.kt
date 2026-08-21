package io.magicstar.uniconv.ui

import androidx.annotation.RequiresApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.magicstar.uniconv.generated.resources.Res
import io.magicstar.uniconv.generated.resources.negatives_not_allowed
import io.magicstar.uniconv.generated.resources.not_a_number
import io.magicstar.uniconv.unit.model.Temperature
import org.jetbrains.compose.resources.stringResource
import java.math.BigDecimal
import java.text.Normalizer

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(9)
@Composable
fun UnitGroup(viewModel: UniconvViewModel, state: UniconvState, unitType: Int, initialUnitText: () -> String, onClick: (io.magicstar.uniconv.unit.model.Unit) -> Unit) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = with(LocalDensity.current) { 15.toDp() })
    ) {
        var selectedUnitText by remember { mutableStateOf(initialUnitText()) }
        var isError by remember { mutableStateOf(false) }
        var supportingText by remember { mutableStateOf("") }
        val errorMessages = listOf(stringResource(Res.string.not_a_number), stringResource(Res.string.negatives_not_allowed))
        OutlinedTextField(
            singleLine = true,
            shape = CircleShape,
            value = (if (unitType == 1) state.value1 else state.value2),
            onValueChange = {
                try {
                    BigDecimal(it)
                    isError = false
                    supportingText = ""
                } catch (_: NumberFormatException) {
                    if (it != "" && it != "-" && it != ".") {
                        isError = true
                        supportingText = errorMessages[0]
                    }
                }
                if (it == "") {
                    isError = false
                    supportingText = ""
                }
                if (!isError && it != "" && it != "." && (it == "-" || BigDecimal(it) < BigDecimal("0")) && state.magnitude !is Temperature) {
                    isError = true
                    supportingText = errorMessages[1]
                }
                if (!isError) {
                    viewModel.onIntent(UniconvIntent.UpdateTextFieldValue(it, unitType))
                    viewModel.onIntent(
                        UniconvIntent.Calculate(
                            state.unit1!!, state.unit2!!, unitType,
                            if (it == "" || it == "-" || it == ".") BigDecimal("0") else BigDecimal(
                                it
                            )
                        )
                    )
                }
            },
            isError = isError,
            supportingText = { Text(supportingText) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            trailingIcon = {
                val colors = OutlinedTextFieldDefaults.colors()
                val interactionSource = remember { MutableInteractionSource() }
                val textStyle = LocalTextStyle.current
                val textColor =
                    textStyle.color.takeOrElse {
                        val focused = interactionSource.collectIsFocusedAsState().value
                        colors.let { if (isError) it.errorTextColor else if (focused) it.focusedTextColor else it.unfocusedTextColor }
                    }
                val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

                ExposedDropdownMenuBox(
                    expanded = isMenuExpanded,
                    onExpandedChange = {
                        isMenuExpanded = it
                        if (isMenuExpanded) selectedUnitText = ""
                    }
                ) {
                    var textFieldHeight by remember { mutableIntStateOf(0) }
                    BasicTextField(
                        modifier = Modifier.width(IntrinsicSize.Min).menuAnchor(
                            ExposedDropdownMenuAnchorType.PrimaryEditable, true).onGloballyPositioned {
                                textFieldHeight = it.size.height
                        },
                        value = selectedUnitText,
                        onValueChange = {
                            selectedUnitText = it
                            isMenuExpanded = true
                        },
                        readOnly = false,
                        singleLine = true,
                        textStyle = mergedTextStyle,
                        cursorBrush = SolidColor(if (isError) colors.errorCursorColor else colors.cursorColor),
                        interactionSource = interactionSource,
                        decorationBox = @Composable { innerTextField ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                VerticalDivider(
                                    Modifier
                                        .heightIn(max = with(LocalDensity.current) { textFieldHeight.toDp() })
                                        .fillMaxHeight(.5f)
                                        .padding(start = 10.dp)
                                )
                                val textMeasurer = rememberTextMeasurer()
                                val density = LocalDensity.current
                                val textStyle = LocalTextStyle.current
                                OutlinedTextFieldDefaults.DecorationBox(
                                    value = selectedUnitText,
                                    visualTransformation = VisualTransformation.None,
                                    innerTextField = innerTextField,
                                    placeholder = {
                                        val extraWidth = with(density) {
                                            val visibleText = textMeasurer.measure(
                                                text = initialUnitText(),
                                                maxLines = 1,
                                                style = textStyle,
                                                density = density
                                            )
                                            val lineEndIndex = visibleText.getLineEnd(0, true)
                                            textMeasurer.measure(
                                                text = initialUnitText().substring(lineEndIndex),
                                                maxLines = 1,
                                                style = textStyle,
                                                density = density,
                                                softWrap = false,
                                                overflow = TextOverflow.Visible,

                                                ).size.width.toDp().value + (if (lineEndIndex != initialUnitText().lastIndex) 16 else 0)
                                        }

                                        Text(
                                            modifier = Modifier.padding(end = extraWidth.dp),
                                            text = initialUnitText(),
                                            maxLines = 1,
                                            softWrap = false,
                                            overflow = TextOverflow.Visible
                                        )},
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            isMenuExpanded
                                        )
                                    },
                                    singleLine = true,
                                    enabled = true,
                                    isError = false,
                                    interactionSource = interactionSource,
                                    colors = colors,
                                    container = {},
                                )
                            }
                        },
                    )


                    if (!isMenuExpanded) selectedUnitText = initialUnitText()
                    val tempReference = state.reference!!.filter { "${stringResource(it.name)} (${it.abbreviation})".normalize().contains(selectedUnitText.normalize(), true) }
                    if (tempReference.isNotEmpty())
                        ExposedDropdownMenu(
                            modifier = Modifier.exposedDropdownSize(false),
                            scrollState = rememberScrollState(),
                            expanded = isMenuExpanded,
                            onDismissRequest = {
                                isMenuExpanded = false
                                keyboardController?.hide()
                            }
                        ) {
                            tempReference.forEach { unit ->
                                DropdownMenuItem(
                                    text = { Text("${stringResource(unit.name)} (${unit.abbreviation})") },
                                    onClick = {
                                        onClick(unit)
                                        isMenuExpanded = false
                                    }
                                )
                            }
                        }

                }

            }
        )
    }
}

@RequiresApi(9)
fun String.normalize(): String {
    val unaccentRegex = "\\p{InCombiningDiacriticalMarks}+".toRegex()
    return unaccentRegex.replace(Normalizer.normalize(this, Normalizer.Form.NFD), "")
}