package io.magicstar.uniconv.ui

import androidx.annotation.RequiresApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import java.text.Normalizer

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(9)
@Composable
fun UnitGroup(state: UniconvState, unitType: Int, initialUnitText: () -> String, onValueChange: (String) -> Unit, onClick: (io.magicstar.uniconv.unit.model.Unit) -> Unit) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.padding(
                start = 50.dp,
                top = 15.dp,
                bottom = 15.dp,
                end = 4.dp
            ),
            singleLine = true,
            shape = CircleShape,
            interactionSource = interactionSource,
            value = (if (unitType == 1) state.value1 else state.value2),
            onValueChange = {
                onValueChange(it)
            }
        )

        val measurer = rememberTextMeasurer()
        val textStyle = LocalTextStyle.current
        val unitSelectorWidth by remember {
            mutableIntStateOf(
                measurer.measure(
                    text = state.let { if (unitType == 1) it.unit1!! else it.unit2!! }.abbreviation,
                    style = textStyle,
                    maxLines = 1,
                    softWrap = true
                ).size.width
            )
        }

        var selectedUnitText by remember { mutableStateOf(initialUnitText()) }
        ExposedDropdownMenuBox(
            modifier = Modifier
                .width((16+unitSelectorWidth+16+48+12).dp)
                .padding(
                    start = 10.dp,
                    end = 4.dp,
                    top = 15.dp,
                    bottom = 15.dp
                ),
            expanded = isMenuExpanded,
            onExpandedChange = {
                isMenuExpanded = it
                if (isMenuExpanded) selectedUnitText = ""
            }
        ) {
            OutlinedTextField(
                modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true),
                shape = CircleShape,
                value = selectedUnitText,
                onValueChange = {
                    selectedUnitText = it
                    isMenuExpanded = true
                },
                readOnly = false,
                singleLine = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(isMenuExpanded) }
            )
            if (!isMenuExpanded) selectedUnitText = initialUnitText()
            val tempReference = state.reference!!.filter { "${stringResource(it.name)} (${it.abbreviation})".normalize().contains(selectedUnitText.normalize(), true) }
            if (tempReference.isNotEmpty())
                ExposedDropdownMenu(
                    modifier = Modifier.exposedDropdownSize(false),
                    scrollState = rememberScrollState(),
                    expanded = isMenuExpanded,
                    onDismissRequest = { isMenuExpanded = false }
                ) {
                    tempReference.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text("${stringResource(unit.name)} (${unit.abbreviation})") },
                            onClick = {
                                onClick(unit)
                                isMenuExpanded = false
                            },
                            interactionSource = interactionSource
                        )
                    }
                }

        }
    }
}

@RequiresApi(9)
fun String.normalize(): String {
    val unaccentRegex = "\\p{InCombiningDiacriticalMarks}+".toRegex()
    return unaccentRegex.replace(Normalizer.normalize(this, Normalizer.Form.NFD), "")
}