package com.obidia.mocktest.domain.repo

import com.obidia.mocktest.domain.entity.ProductDaoEntity
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun readAllData(): Flow<MutableList<ProductDaoEntity>>

    suspend fun addUser(note: ProductDaoEntity)

    suspend fun updateUser(note: ProductDaoEntity)

    suspend fun deleteUser(note: ProductDaoEntity)

}