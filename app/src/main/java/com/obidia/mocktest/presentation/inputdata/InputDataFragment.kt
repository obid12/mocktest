package com.obidia.mocktest.presentation.inputdata

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.obidia.mocktest.R
import com.obidia.mocktest.databinding.FragmentInputDataBinding
import com.obidia.mocktest.presentation.NoteViewModel
import com.obidia.mocktest.presentation.UiEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*


class InputDataFragment : DialogFragment() {

    private val noteViewModel by activityViewModels<NoteViewModel>()
    private var _binding: FragmentInputDataBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_MockTest_Fullscreen)

        dialog?.let {
            it.window?.apply {
                if (setDialogPosition() != Gravity.NO_GRAVITY) {
                    val width = ViewGroup.LayoutParams.MATCH_PARENT
                    val height = ViewGroup.LayoutParams.WRAP_CONTENT
                    it.window?.apply {
                        val wlp: WindowManager.LayoutParams = attributes
                        wlp.gravity = setDialogPosition()
                        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()

                        setLayout(width, height)
                        setBackgroundDrawableResource(R.drawable.bg_dialog)
                        setStyle(STYLE_NORMAL, R.style.Theme_MockTest_Fullscreen)

                        attributes = wlp
                    }
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInputDataBinding.inflate(inflater, container, false)
        binding.setTanggal.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(childFragmentManager, "DatePicker")

            datePicker.addOnPositiveButtonClickListener {
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                val date = dateFormatter.format(Date(it))
                Toast.makeText(requireContext(), "$date is selected", Toast.LENGTH_LONG).show()
                binding.setTanggal.text = date

            }

            datePicker.addOnNegativeButtonClickListener {
                Toast.makeText(
                    requireContext(),
                    "${datePicker.headerText} is cancelled",
                    Toast.LENGTH_LONG
                ).show()
            }

            datePicker.addOnCancelListener {
                Toast.makeText(requireContext(), "Date Picker Cancelled", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnClose.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnAdd.setOnClickListener {
            insertDataToDatabase()
        }
        return binding.root
    }


    private fun insertDataToDatabase() {
        noteViewModel.apply {
            if (binding.setTanggal.text.toString() == "dd/mm/yyyy") {
                setTanggal("")
            } else {
                setTanggal(binding.setTanggal.text.toString())
            }
            setNamaBarang(binding.etNamaBarang.text.toString())
            setPemasok(binding.etNamaPemasok.text.toString())
            setDetailBarang(binding.etDetail.text.toString())
            setStokBarang(binding.etStok.text.toString())
            addUser()
        }
        observe()
    }

    private fun observe() {
        noteViewModel.eventFlow.flowWithLifecycle(lifecycle)
            .onEach { state -> handleState(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: UiEvent) {
        when (state) {
            is UiEvent.SaveNote -> {
                Toast.makeText(requireContext(), "Input Data Berhasil", Toast.LENGTH_LONG).show()
                dialog?.dismiss()
            }
            is UiEvent.ShowSnackbar -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    open fun setDialogPosition(): Int {
        return Gravity.NO_GRAVITY
    }


}


