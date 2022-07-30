package com.dev_marinov.wnews.presentation.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.*
import androidx.transition.*
import com.dev_marinov.wnews.CircularRevealTransition
import com.dev_marinov.wnews.R
import com.dev_marinov.wnews.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var sceneFirst: ViewGroup
    lateinit var sceneSecond: ViewGroup
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        hideSystemUI()

        setUpViews(binding.rootContainer)

        lifecycleScope.launch(Dispatchers.Main) {
            showSceneProgressBar(binding.rootContainer)
            showSceneNextFragment(binding.rootContainer, progressBar)
        }
    }

    private fun setUpViews(rootContainer: RelativeLayout) {
        (layoutInflater.inflate(
            R.layout.scene_animation_1,
            rootContainer,
            false
        ) as? ViewGroup)?.let {
            sceneFirst = it
        }
        (layoutInflater.inflate(
            R.layout.scene_animation_2,
            rootContainer,
            false
        ) as? ViewGroup)?.let {
            sceneSecond = it
        }
        progressBar = sceneFirst.findViewById(R.id.progress_bar_scene)
    }

    private suspend fun showSceneProgressBar(rootContainer: RelativeLayout) {
        progressBar.visibility = View.VISIBLE

        val scene = Scene(rootContainer, sceneFirst)
        TransitionManager.go(scene, null) // делаем транзакцию в следующую scene_animation_2

        delay(2000)
        progressBar.visibility = View.INVISIBLE
    }

    private suspend fun showSceneNextFragment(
        rootContainer: RelativeLayout,
        progressBar: ProgressBar
    ) {
        val scene = Scene(rootContainer, sceneSecond)
        val transition = makeCircularTransition(rootContainer)
        TransitionManager.go(scene, transition)

        delay(500)
        val intent = Intent(this@MainActivity, NewsActivity::class.java)
        startActivity(intent)
    }

    private fun makeCircularTransition(rootContainer: RelativeLayout): Transition {
        val transitionSet = TransitionSet()

        val changeBounds = ChangeBounds()
        changeBounds.addListener(object : TransitionListenerAdapter() {
            override fun onTransitionEnd(transition: Transition) {
                rootContainer.findViewById<View>(R.id.progress_bar_scene).visibility =
                    View.INVISIBLE
            }
        })

        changeBounds.addTarget(R.id.progress_bar_scene)
        changeBounds.duration = 300

        val arcMotion = ArcMotion()
        arcMotion.maximumAngle = 45F
        arcMotion.minimumHorizontalAngle = 90F
        arcMotion.minimumVerticalAngle = 0F

        changeBounds.setPathMotion(arcMotion)
        transitionSet.addTransition(changeBounds)

        // прменяем созданный класс анимации CircularRevealTransition
        val circularRevealTransition = CircularRevealTransition()
        circularRevealTransition.addTarget(R.id.cl_scene)
        circularRevealTransition.startDelay = 200
        circularRevealTransition.duration = 600
        transitionSet.addTransition(circularRevealTransition)

        return transitionSet
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
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        }
    }
}