package com.dev_marinov.wnews

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.transition.Visibility
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.transition.TransitionValues
import kotlin.math.hypot

class CircularRevealTransition : Visibility() {

    override fun onAppear(
        sceneRoot: ViewGroup?,
        view: View,
        startValues: TransitionValues?,
        endValues: TransitionValues?): Animator? {
        val startRadius = 0
        val endRadius = hypot(view.width.toDouble(), view.height.toDouble()).toInt()
        val reveal = ViewAnimationUtils.createCircularReveal(
            view,
            view.width / 2,
            view.height / 2,
            startRadius.toFloat(),
            endRadius.toFloat()
        )
        // делаем представление невидимым до тех пор, пока не начнется анимация
        view.alpha = 0f
        reveal.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                view.alpha = 1f
            }
        })
        return reveal
    }

    override fun onDisappear(
        sceneRoot: ViewGroup?,
        view: View,
        startValues: TransitionValues?,
        endValues: TransitionValues?): Animator? {
        val endRadius = 0
        val startRadius = hypot(view.width.toDouble(), view.height.toDouble()).toInt()
        return ViewAnimationUtils.createCircularReveal(
            view,
            view.width / 2,
            view.height / 2,
            startRadius.toFloat(),
            endRadius.toFloat()
        )
    }


}