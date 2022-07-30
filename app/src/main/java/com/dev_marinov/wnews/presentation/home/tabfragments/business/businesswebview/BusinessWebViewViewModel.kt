package com.dev_marinov.wnews.presentation.home.tabfragments.business.businesswebview

import androidx.lifecycle.ViewModel
import java.util.*

class BusinessWebViewViewModel : ViewModel(){

    var backStatus = false
    var urlListStack: Stack<String> = Stack()
}