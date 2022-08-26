package com.krasovitova.currencywallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationView
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

        findViewById<NavigationView>(R.id.navigation_view).setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_graph -> replaceFragment(fragment = GraphFragment())
                R.id.item_set -> Unit
            }
            findViewById<DrawerLayout>(R.id.drawer_layout).close()
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, fragment).addToBackStack(null)
        }
    }
}