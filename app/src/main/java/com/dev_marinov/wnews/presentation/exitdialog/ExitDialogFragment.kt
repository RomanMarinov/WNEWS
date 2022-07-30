package com.dev_marinov.wnews.presentation.exitdialog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dev_marinov.wnews.databinding.FragmentExitDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class ExitDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentExitDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        Log.e("333", "создалась ExitDialogFragment")
        binding = FragmentExitDialogBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
    }

    private fun setOnClickListener(){
        binding.btNo.setOnClickListener {
            Log.e("333","btNo")
            dismiss()
        }
        binding.btYes.setOnClickListener{
            Log.e("333","btYes")
            requireActivity().finish()
            exitProcess(0)
        }
    }
}