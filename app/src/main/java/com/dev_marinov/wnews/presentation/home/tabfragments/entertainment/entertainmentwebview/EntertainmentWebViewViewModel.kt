package com.dev_marinov.wnews.presentation.home.tabfragments.entertainment.entertainmentwebview

import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class EntertainmentWebViewViewModel : ViewModel(){

    var backStatus = false
    var urlListStack: Stack<String> = Stack()
}