package com.dev_marinov.wnews.presentation.exitdialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dev_marinov.wnews.databinding.FragmentExitDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExitDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentExitDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExitDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btNo.setOnClickListener {
            dismiss()
        }
        binding.btYes.setOnClickListener {
            requireActivity().finishAndRemoveTask()
        }
    }
}