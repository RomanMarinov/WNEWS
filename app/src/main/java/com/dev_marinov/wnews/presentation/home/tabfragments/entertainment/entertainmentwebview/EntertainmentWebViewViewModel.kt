package com.dev_marinov.wnews.presentation.home.tabfragments.entertainment.entertainmentwebview

import androidx.lifecycle.ViewModel
import java.util.*

class EntertainmentWebViewViewModel : ViewModel(){

    var backStatus = false
    var urlListStack: Stack<String> = Stack()
}