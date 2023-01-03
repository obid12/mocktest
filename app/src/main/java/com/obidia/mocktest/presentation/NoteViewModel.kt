package com.obidia.mocktest.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obidia.mocktest.domain.entity.InvalidNoteException
import com.obidia.mocktest.domain.entity.ProductDaoEntity
import com.obidia.mocktest.domain.usecase.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val useCase: ProductUseCase
) :
    ViewModel() {

    fun readAllData() = useCase.getData()

    private val _selesai = MutableLiveData<Boolean>()

    fun setSelesai(data: Boolean) {
        _selesai.value = data
    }

    private val _namaBarang = MutableLiveData<String>()

    fun setNamaBarang(data: String) {
        _namaBarang.value = data
    }

    private val _pemasok = MutableLiveData<String>()

    fun setPemasok(data: String) {
        _pemasok.value = data
    }

    private val _detailBarang = MutableLiveData<String>()

    fun setDetailBarang(data: String) {
        _detailBarang.value = data
    }

    private val _stokBarang = MutableLiveData<String>()

    fun setStokBarang(data: String) {
        _stokBarang.value = data
    }

    private val _tgl = MutableLiveData<String>()

    fun setTanggal(data: String) {
        _tgl.value = data
    }

    private val _id = MutableLiveData<Long>()

    fun setId(data: Long) {
        _id.value = data
    }


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow get() = _eventFlow


    fun addUser() {
        val productDaoEntity =
            ProductDaoEntity(
                0,
                _namaBarang.value.toString(),
                _stokBarang.value.toString(),
                _detailBarang.value.toString(),
                _pemasok.value.toString(),
                _tgl.value.toString()
            )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                useCase.addData(productDaoEntity)
                _eventFlow.emit(UiEvent.SaveNote)
            } catch (e: InvalidNoteException) {
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        message = e.message ?: "Couldn't save note"
                    )
                )
            }
        }
    }

    fun updateUser() {
        val productDaoEntity =
            _id.value?.toLong()?.let { id ->
                ProductDaoEntity(
                    id,
                    _namaBarang.value.toString(),
                    _stokBarang.value.toString(),
                    _detailBarang.value.toString(),
                    _pemasok.value.toString(),
                    _tgl.value.toString()
                )
            }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                productDaoEntity?.let { useCase.updateData(it) }
                _eventFlow.emit(UiEvent.SaveNote)
            } catch (e: InvalidNoteException) {
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        message = e.message ?: "Couldn't save note"
                    )
                )
            }

        }
    }

    fun deleteUser(noteEntity: ProductDaoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.deleteUser(noteEntity)
        }
    }


}

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    object SaveNote : UiEvent()
}