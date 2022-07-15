package com.dev_marinov.wnews.presentation.search.searchwebview

import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class SearchWebViewViewModel : ViewModel(){

    var backStatus = false
    var urlListStack: Stack<String> = Stack()
}