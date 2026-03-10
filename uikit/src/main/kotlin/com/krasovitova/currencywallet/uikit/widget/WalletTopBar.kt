package com.krasovitova.currencywallet.uikit.widget

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletTopBar(
    modifier: Modifier = Modifier,
    text: Int,
    navigationIcon: ImageVector? = null,
    actions: ImageVector? = null,
    onImageClick: (() -> Unit)? = null,
    onActionClick: (() -> Unit)? = null
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(text),
                fontWeight = FontWeight.SemiBold,
            )
        },
        navigationIcon = {
            navigationIcon?.let {
                Icon(
                    modifier = Modifier
                        .clickable(onClick = { onImageClick?.invoke() })
                        .padding(8.dp),
                    imageVector = it,
                    contentDescription = null,
                )
            }
        },
        actions = {
            actions?.let {
                Icon(
                    modifier = Modifier
                        .clickable(onClick = { onActionClick?.invoke() })
                        .padding(8.dp),
                    imageVector = it,
                    contentDescription = null,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
    )
}