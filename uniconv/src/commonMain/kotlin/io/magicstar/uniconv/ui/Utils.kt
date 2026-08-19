package io.magicstar.uniconv.ui

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import java.text.Normalizer

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(9)
@Composable
fun UnitGroup(modifier: Modifier = Modifier, state: UniconvState, unitType: Int, initialUnitText: () -> String, onValueChange: (String) -> Unit, onClick: (io.magicstar.uniconv.unit.model.Unit) -> Unit) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(top = with(LocalDensity.current) { 15.toDp() })
    ) {
        OutlinedTextField(
            singleLine = true,
            shape = CircleShape,
            value = (if (unitType == 1) state.value1 else state.value2),
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
        )

        var selectedUnitText by remember { mutableStateOf(initialUnitText()) }
        ExposedDropdownMenuBox(
            modifier = Modifier.padding(start = 10.dp),
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
                placeholder = { Text(initialUnitText()) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(isMenuExpanded) }
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
}

@RequiresApi(9)
fun String.normalize(): String {
    val unaccentRegex = "\\p{InCombiningDiacriticalMarks}+".toRegex()
    return unaccentRegex.replace(Normalizer.normalize(this, Normalizer.Form.NFD), "")
}