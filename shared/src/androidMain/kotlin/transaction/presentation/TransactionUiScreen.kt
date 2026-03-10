package com.krasovitova.currencywallet.transaction.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krasovitova.currencywallet.shared.R
import com.krasovitova.currencywallet.uikit.theme.CurrencyWalletTheme
import com.krasovitova.currencywallet.uikit.theme.widget.WalletInput
import com.krasovitova.currencywallet.uikit.widget.WalletBigButton
import com.krasovitova.currencywallet.uikit.widget.WalletTopBar
import org.koin.compose.viewmodel.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionUiScreen(
    onBackClick: () -> Unit,//TODO брать из вью модели
) {
    val viewModel: TransactionViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) { //TODO вынести в отдельную
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        viewModel.updateDate(
                            SimpleDateFormat(
                                "dd.MM.yyyy",
                                Locale.getDefault()
                            ).format(java.util.Date(millis))
                        )
                    }
                    showDatePicker = false
                }) { Text(stringResource(android.R.string.ok)) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(android.R.string.cancel))
                }
            },
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            WalletTopBar(
                text = R.string.transaction_screen_title,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onImageClick = onBackClick
            )
        },
        bottomBar = {
            WalletBigButton(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = viewModel::saveTransaction,
                text = stringResource(R.string.transaction_add_button)
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        Content(
            state = state,
            paddingValues = paddingValues,
            onDatePickerRequest = { showDatePicker = true },
            currencies = viewModel.currencies,
            transactionTypes = viewModel.transactionTypes,
            onSumChanged = viewModel::updateSum,
            onCurrencyChanged = viewModel::updateCurrency,
            onTypeChanged = viewModel::updateType,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    state: TransactionState,
    paddingValues: PaddingValues,
    currencies: List<String>,
    transactionTypes: List<String>,
    sumError: String? = null,
    currencyError: String? = null,
    dateError: String? = null,
    typeError: String? = null,
    onSumChanged: (String) -> Unit,
    onCurrencyChanged: (String) -> Unit,
    onTypeChanged: (String) -> Unit,
    onDatePickerRequest: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
    ) {
        EnteredAmount(
            sum = state.sum,
            currency = state.currency
        )
        TransactionInputs(
            sum = state.sum,
            currency = state.currency,
            date = state.date,
            type = state.type,
            dateError = dateError,
            typeError = typeError,
            onSumChanged = onSumChanged,
            onTypeChanged = onTypeChanged,
            onCurrencyChanged = onCurrencyChanged,
            onDatePickerRequest = onDatePickerRequest,
            sumError = sumError,
            currencyError = currencyError,
            currencies = currencies,
            transactionTypes = transactionTypes
        )
    }
}

@Composable
private fun EnteredAmount(
    sum: String,
    currency: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.transaction_enter_amount),
            color = CurrencyWalletTheme.colors.onPrimary.copy(alpha = 0.75f),
            style = CurrencyWalletTheme.typography.labelLarge,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = sum.ifBlank { ZERO },
            color = CurrencyWalletTheme.colors.onPrimary,
            style = CurrencyWalletTheme.typography.displayLarge,
        )
        if (currency.isNotBlank()) {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = currency,
                color = CurrencyWalletTheme.colors.onPrimary.copy(alpha = 0.75f),
                style = CurrencyWalletTheme.typography.bodyLarge,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransactionInputs(
    sum: String,
    date: String,
    currency: String,
    type: String,
    currencies: List<String>,
    transactionTypes: List<String>,
    sumError: String? = null,
    currencyError: String? = null,
    dateError: String? = null,
    typeError: String? = null,
    onSumChanged: (String) -> Unit,
    onCurrencyChanged: (String) -> Unit,
    onTypeChanged: (String) -> Unit,
    onDatePickerRequest: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            WalletInput(
                text = sum,
                onValueChange = onSumChanged,
                label = stringResource(R.string.transaction_label_sum),
                error = sumError,
                keyboardType = KeyboardType.Decimal,
            )
            TransactionDropdownMenu(
                text = currency,
                onValueChange = onCurrencyChanged,
                items = currencies,
                error = currencyError,
                label = stringResource(R.string.transaction_label_currency),
            )
            WalletInput(
                text = date,
                onValueChange = { },
                label = stringResource(R.string.transaction_label_date),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = onDatePickerRequest) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                },
                error = dateError,
            )
            TransactionDropdownMenu(
                text = type,
                onValueChange = onTypeChanged,
                items = transactionTypes,
                error = typeError,
                label = stringResource(R.string.transaction_label_type),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransactionDropdownMenu(
    text: String,
    onValueChange: (String) -> Unit,
    items: List<String>,
    error: String? = null,
    label: String
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        WalletInput(
            text = text,
            onValueChange = onValueChange,
            label = label,
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            error = error,
            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onValueChange(item)
                        expanded = false
                    },
                )
            }
        }
    }
}

private const val ZERO = "0"
