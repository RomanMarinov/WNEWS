package com.dev_marinov.wnews.presentation.home.tabfragments.technology.technologywebview
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class TechnologyWebViewViewModel : ViewModel(){

    var backStatus = false
    var urlListStack: Stack<String> = Stack()
}