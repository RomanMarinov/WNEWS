package com.dev_marinov.wnews.presentation.home.tabfragments.science.sciencewebview

import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class ScienceWebViewViewModel : ViewModel(){

    var backStatus = false
    var urlListStack: Stack<String> = Stack()
}