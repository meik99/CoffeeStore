package com.rynkbit.coffeestore.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Customer(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "balance") var balance: Double = 0.toDouble()
){
    constructor(
        id: Int,
        name: String
    ): this(id, name, 0.toDouble())
}