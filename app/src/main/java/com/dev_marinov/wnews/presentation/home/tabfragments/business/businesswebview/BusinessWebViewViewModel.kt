package com.dev_marinov.wnews.presentation.home.tabfragments.business.businesswebview

import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class BusinessWebViewViewModel : ViewModel(){

    var backStatus = false
    var urlListStack: Stack<String> = Stack()
}