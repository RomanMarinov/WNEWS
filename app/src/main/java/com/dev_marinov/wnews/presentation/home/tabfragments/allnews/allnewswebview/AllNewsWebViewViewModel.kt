package com.dev_marinov.geniussonglyrics.presentation.webview

import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class AllNewsWebViewViewModel : ViewModel(){

    var backStatus = false
    var urlListStack: Stack<String> = Stack()
}