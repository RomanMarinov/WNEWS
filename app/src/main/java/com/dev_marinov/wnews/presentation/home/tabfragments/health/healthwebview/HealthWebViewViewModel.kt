package com.dev_marinov.wnews.presentation.home.tabfragments.health.healthwebview

import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class HealthWebViewViewModel : ViewModel(){

    var backStatus = false
    var urlListStack: Stack<String> = Stack()
}