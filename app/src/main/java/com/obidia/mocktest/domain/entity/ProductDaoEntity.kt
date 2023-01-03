package com.obidia.mocktest.domain.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "product_data")
data class ProductDaoEntity(
    @PrimaryKey(autoGenerate = true)
    val idProduct: Long = 0L,

    @ColumnInfo(name = "nama_barang")
    val namaBarang: String,

    @ColumnInfo(name = "stok_barang")
    val stokBarang: String,

    @ColumnInfo(name = "detail_barang")
    val detailBarang: String,

    @ColumnInfo(name = "pemasok")
    val pemasok: String,

    @ColumnInfo(name = "tanggal")
    val tanggal: String
) : Parcelable

class InvalidNoteException(message: String) : Exception(message)
