package com.krasovitova.currencywallet.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.krasovitova.currencywallet.Constants.TRANSACTION_ID_ARG
import com.krasovitova.currencywallet.transaction.presentation.SaveTransactionError
import com.krasovitova.currencywallet.transaction.presentation.TransactionScreenEvent
import com.krasovitova.currencywallet.transaction.presentation.TransactionUiScreen
import com.krasovitova.currencywallet.transaction.presentation.TransactionViewModel
import com.krasovitova.currencywallet.uikit.theme.CurrencyWalletTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionFragment : Fragment() {
    private val viewModel: TransactionViewModel by viewModel()

    private var validationErrors by mutableStateOf<List<SaveTransactionError>>(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            CurrencyWalletTheme {
               // val errors = validationErrors
                TransactionUiScreen(
                    onBackClick = { requireActivity().onBackPressedDispatcher.onBackPressed() },
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transactionId = arguments?.getInt(TRANSACTION_ID_ARG, 0) ?: 0

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.event.collect { event ->
                when (event) {
                    TransactionScreenEvent.NavigateBack ->
                        requireActivity().onBackPressedDispatcher.onBackPressed()

                    is TransactionScreenEvent.ValidationFailed ->
                        validationErrors = event.errors
                }
            }
        }

        if (transactionId != 0) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.fetchTransaction(transactionId)
            }
        }
    }
}
