package com.obidia.mocktest.presentation.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.obidia.mocktest.R
import com.obidia.mocktest.databinding.FragmentUpdateDataBinding
import com.obidia.mocktest.domain.entity.ProductDaoEntity
import com.obidia.mocktest.presentation.NoteViewModel
import com.obidia.mocktest.presentation.UiEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*

class UpdateDataFragment(private val noteEntity: ProductDaoEntity?) : DialogFragment() {

    private val noteViewModel by activityViewModels<NoteViewModel>()
    private var _binding: FragmentUpdateDataBinding? = null
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateDataBinding.inflate(inflater, container, false)

        binding.btnDelete.setOnClickListener {
            dialogDelete()
            dialog?.dismiss()
        }
        binding.apply {
            etNamaBarang.setText(noteEntity?.namaBarang)
            etNamaPemasok.setText(noteEntity?.pemasok)
            etDetail.setText(noteEntity?.detailBarang)
            etStok.setText(noteEntity?.stokBarang)
            setTanggal.setText(noteEntity?.tanggal)
        }

        binding.setTanggal.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(childFragmentManager, "DatePicker")

            datePicker.addOnPositiveButtonClickListener {
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                val date = dateFormatter.format(Date(it))
                binding.setTanggal.text = date

            }

        }

        binding.btnedit.setOnClickListener {
            updateData()
        }


        return binding.root


    }

    private fun updateData() {
        noteViewModel.apply {
            setId(noteEntity?.idProduct!!)
            if (binding.setTanggal.text.toString() == "dd/mm/yyyy") {
                setTanggal("")
            } else {
                setTanggal(binding.setTanggal.text.toString())
            }
            setNamaBarang(binding.etNamaBarang.text.toString())
            setPemasok(binding.etNamaPemasok.text.toString())
            setDetailBarang(binding.etDetail.text.toString())
            setStokBarang(binding.etStok.text.toString())
            updateUser()
        }
        observe()
    }

    private fun dialogDelete() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->

            noteViewModel.deleteUser(noteEntity!!)
            dialog?.dismiss()

        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete Product")
        builder.setMessage("Apakah kamu akan menghapus product ${noteEntity?.namaBarang}")
        builder.create().show()
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