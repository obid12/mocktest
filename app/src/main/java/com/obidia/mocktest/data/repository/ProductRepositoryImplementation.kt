package com.obidia.mocktest.data.repository

import com.obidia.mocktest.data.database.ProductDao
import com.obidia.mocktest.domain.entity.ProductDaoEntity
import com.obidia.mocktest.domain.repo.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImplementation @Inject constructor(private val noteDao: ProductDao) :
    ProductRepository {
    override fun readAllData(): Flow<MutableList<ProductDaoEntity>> {
        return noteDao.readAllData()
    }

    override suspend fun addUser(note: ProductDaoEntity) {
        return noteDao.addUser(note)
    }

    override suspend fun updateUser(note: ProductDaoEntity) {
        return noteDao.updateUser(note)
    }

    override suspend fun deleteUser(note: ProductDaoEntity) {
        return noteDao.deleteUser(note)
    }

}