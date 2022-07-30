package com.dev_marinov.wnews.presentation

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.dev_marinov.wnews.R
import com.dev_marinov.wnews.databinding.ActivityNewsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


import androidx.navigation.NavDestination

import androidx.navigation.NavBackStackEntry

import androidx.activity.OnBackPressedCallback




@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)

        hideSystemUI()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        // Настраиваем нижний вид навигации с помощью navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)

        // Настраиваем ActionBar с navController и 3 пунктами назначения верхнего уровня
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.viewPager2Fragment,
                R.id.favoritesFragment,
                R.id.searchFragment
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun hideSystemUI() {
        // если сдк устройства больше или равно сдк приложения
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    )
        }
    }

//    override fun onBackPressed() {
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment?
//        navHostFragment?.let {
//            val backStackEntryCount = navHostFragment.childFragmentManager.backStackEntryCount
//            if (backStackEntryCount > 0) { super.onBackPressed() } else { showExitDialog() }
//        }
//    }

    private fun showExitDialog(){
       // binding.fragmentContainerView.findNavController().navigate(R.id.action_viewPager2Fragment_to_exitDialogFragment)
    }
}

// 1. как сделать бот нав вью с разными бекстками
// 2. как перейти на диалог из нав графа (deeplink)
// 3. как сделать чтобы не переходило на майнакт



