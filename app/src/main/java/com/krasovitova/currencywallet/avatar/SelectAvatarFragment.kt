package com.krasovitova.currencywallet.avatar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.krasovitova.currencywallet.base.BaseFragment
import com.krasovitova.currencywallet.databinding.FragmentImagesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectAvatarFragment : BaseFragment<FragmentImagesBinding>(
    FragmentImagesBinding::inflate
) {

    private val viewModel: SelectAvatarViewModel by viewModels()

    private val avatarAdapter = AvatarAdapter() {
        //TODO() implement avatar download
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerImages.adapter = avatarAdapter

        viewModel.images.observe(viewLifecycleOwner) {
            avatarAdapter.submitList(it)
        }

        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}