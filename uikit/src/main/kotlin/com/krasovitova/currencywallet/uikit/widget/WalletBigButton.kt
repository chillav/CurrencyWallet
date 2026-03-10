package com.krasovitova.currencywallet.uikit.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krasovitova.currencywallet.uikit.R
import com.krasovitova.currencywallet.uikit.theme.CurrencyWalletTheme

@Composable
fun WalletBigButton(
    modifier: Modifier = Modifier,
   onClick: () -> Unit,
   text: String,
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
    ) {
        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = text,
            style = CurrencyWalletTheme.typography.bigButton,
        )
    }
}