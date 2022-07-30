package com.dev_marinov.wnews.presentation.home.tabfragments.health.healthwebview

import androidx.lifecycle.ViewModel
import java.util.*

class HealthWebViewViewModel : ViewModel(){

    var backStatus = false
    var urlListStack: Stack<String> = Stack()
}