package com.dev_marinov.wnews.presentation.home.tabfragments.science.sciencewebview

import androidx.lifecycle.ViewModel
import java.util.*

class ScienceWebViewViewModel : ViewModel(){

    var backStatus = false
    var urlListStack: Stack<String> = Stack()
}