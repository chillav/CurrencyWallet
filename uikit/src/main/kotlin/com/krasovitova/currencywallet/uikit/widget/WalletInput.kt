package com.krasovitova.currencywallet.uikit.theme.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletInput(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    label: String,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    error: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        cursorColor = MaterialTheme.colorScheme.primary,
    )

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = onValueChange,
        readOnly = readOnly,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        label = { Text(text = label) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = error != null,
        supportingText = error?.let { { Text(it) } },
        shape = shape,
        colors = fieldColors,
    )
}