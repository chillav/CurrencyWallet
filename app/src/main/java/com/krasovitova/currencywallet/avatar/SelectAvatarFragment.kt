package com.krasovitova.currencywallet.avatar

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.krasovitova.currencywallet.Constants.AVATAR_CHANGED_RESULT
import com.krasovitova.currencywallet.Constants.AVATAR_CHANGED_RESULT_ARG
import com.krasovitova.currencywallet.Constants.AVATAR_IMAGE_URL
import com.krasovitova.currencywallet.Constants.SHARED_PREFERENCES
import com.krasovitova.currencywallet.base.BaseFragment
import com.krasovitova.currencywallet.databinding.FragmentSelectAvatarBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SelectAvatarFragment : BaseFragment<FragmentSelectAvatarBinding>(
    FragmentSelectAvatarBinding::inflate
) {

    private val viewModel: SelectAvatarViewModel by viewModels()

    private val avatarAdapter = AvatarAdapter {
        activity?.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
            ?.edit()
            ?.apply {
                putString(AVATAR_IMAGE_URL, it.url)
                commit()
            }

        setFragmentResult(
            AVATAR_CHANGED_RESULT,
            bundleOf(AVATAR_CHANGED_RESULT_ARG to it.url)
        )
        activity?.onBackPressed()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerImages.adapter = avatarAdapter

        viewModel.images.observe(viewLifecycleOwner) {
            avatarAdapter.submitList(it)
            binding.progressBar.progressBar.visibility = View.INVISIBLE
        }

        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}