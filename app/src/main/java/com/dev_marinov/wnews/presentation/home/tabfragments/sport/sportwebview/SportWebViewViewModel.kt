package com.dev_marinov.wnews.presentation.home.tabfragments.sport.sportwebview

import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class SportWebViewViewModel : ViewModel(){

    var backStatus = false
    var urlListStack: Stack<String> = Stack()
}