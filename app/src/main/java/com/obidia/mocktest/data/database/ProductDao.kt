package com.obidia.mocktest.data.database

import androidx.room.*
import com.obidia.mocktest.domain.entity.ProductDaoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(data: ProductDaoEntity)

    @Update
    suspend fun updateUser(data: ProductDaoEntity)

    @Delete
    suspend fun deleteUser(data: ProductDaoEntity)

    @Query("SELECT * FROM product_data ORDER BY idProduct ASC")
    fun readAllData(): Flow<MutableList<ProductDaoEntity>>


}