package com.krasovitova.currencywallet

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.navigation.NavigationView
import com.krasovitova.currencywallet.Constants.AVATAR_CHANGED_RESULT
import com.krasovitova.currencywallet.Constants.AVATAR_CHANGED_RESULT_ARG
import com.krasovitova.currencywallet.Constants.AVATAR_IMAGE_URL
import com.krasovitova.currencywallet.avatar.SelectAvatarFragment
import com.krasovitova.currencywallet.graph.GraphFragment
import com.krasovitova.currencywallet.wallet.WalletFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<WalletFragment>(R.id.fragment_container)
        }

        getAvatarImageView().setOnClickListener {
            replaceFragment(fragment = SelectAvatarFragment())
            findViewById<DrawerLayout>(R.id.drawer_layout).close()
        }

        val preferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE)
        val avatarImageUrl = preferences.getString(AVATAR_IMAGE_URL, "")

        if (avatarImageUrl != null && avatarImageUrl.isNotBlank()) {
            setupAvatar(url = avatarImageUrl)
        }

        observeAvatarChanges()

        findViewById<NavigationView>(R.id.navigation_view).setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_graph -> replaceFragment(fragment = GraphFragment())
                R.id.item_set -> Unit
            }
            findViewById<DrawerLayout>(R.id.drawer_layout).close()
            true
        }

        askNotificationPermission()
    }

    private fun setupAvatar(url: String) {
        getAvatarImageView().load(url) {
            transformations(CircleCropTransformation())
        }
    }

    private fun observeAvatarChanges() {
        supportFragmentManager.setFragmentResultListener(
            AVATAR_CHANGED_RESULT,
            this
        ) { _, bundle ->
            bundle.getString(AVATAR_CHANGED_RESULT_ARG)?.let {
                setupAvatar(url = it)
            }
        }
    }

    private fun getAvatarImageView(): ImageView {
        return findViewById<NavigationView>(R.id.navigation_view)
            .getHeaderView(DRAWER_HEADER_POSITION)
            .findViewById(R.id.image_avatar)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, fragment).addToBackStack(null)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        // TODO handle permission result
    }

    private fun askNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (isGranted) {
                // TODO handle permission result
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    companion object {
        const val DRAWER_HEADER_POSITION = 0
    }
}