package com.obidia.mocktest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.obidia.mocktest.domain.entity.ProductDaoEntity

@Database(entities = [ProductDaoEntity::class], version = 3, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun noteDao(): ProductDao

    companion object {
        const val DB_NAME = "products_db"
    }

}