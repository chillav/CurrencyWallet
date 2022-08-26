package com.krasovitova.currencywallet.graph

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.krasovitova.currencywallet.base.BaseFragment
import com.krasovitova.currencywallet.databinding.FragmentGraphBinding

class GraphFragment : BaseFragment<FragmentGraphBinding>(
    FragmentGraphBinding::inflate
) {
    private val viewModel: GraphViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.graphView.setData(viewModel.fetchGraphPoints())
    }
}
