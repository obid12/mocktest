package com.obidia.mocktest.domain.usecase

import com.obidia.mocktest.domain.entity.InvalidNoteException
import com.obidia.mocktest.domain.entity.ProductDaoEntity
import com.obidia.mocktest.domain.repo.ProductRepository
import javax.inject.Inject

class ProductUseCase @Inject constructor(private val repository: ProductRepository) {
    fun getData() = repository.readAllData()

    @Throws(InvalidNoteException::class)
    suspend fun updateData(productDaoEntity: ProductDaoEntity) {
        if (productDaoEntity.namaBarang.isBlank()) {
            throw InvalidNoteException("Barang belom ada")
        }

        if (productDaoEntity.pemasok.isBlank()) {
            throw InvalidNoteException("Pemasok belom ada")
        }

        if (productDaoEntity.detailBarang.isBlank()) {
            throw InvalidNoteException("Detail belom ada")
        }
        if (productDaoEntity.tanggal.isNullOrBlank()) {
            throw InvalidNoteException("Tanggal belom ada")
        }

        if (productDaoEntity.stokBarang.isBlank()) {
            throw InvalidNoteException("Stok belom ada")
        }


        repository.updateUser(productDaoEntity)
    }

    suspend fun deleteUser(productDaoEntity: ProductDaoEntity) =
        repository.deleteUser(productDaoEntity)

    suspend fun addData(productDaoEntity: ProductDaoEntity) {
        if (productDaoEntity.namaBarang.isBlank()) {
            throw InvalidNoteException("Barang belom ada")
        }

        if (productDaoEntity.pemasok.isBlank()) {
            throw InvalidNoteException("Pemasok belom ada")
        }

        if (productDaoEntity.detailBarang.isBlank()) {
            throw InvalidNoteException("Detail belom ada")
        }
        if (productDaoEntity.tanggal.isBlank()) {
            throw InvalidNoteException("Tanggal belom ada")
        }

        if (productDaoEntity.stokBarang.isBlank()) {
            throw InvalidNoteException("Stok belom ada")
        }
        repository.addUser(productDaoEntity)
    }
}