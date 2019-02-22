package com.rynkbit.coffeestore.data.entity

import androidx.room.*
import java.util.*

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Customer::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("customerId"),
            onDelete = ForeignKey.SET_DEFAULT
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("productId"),
            onDelete = ForeignKey.SET_DEFAULT
        )
    ),
    indices = arrayOf(
        Index(
            "customerId"
        ),
        Index(
            "productId"
        )
    )
)
data class CustomerTransaction(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "customerId") var customerId: Int,
    @ColumnInfo(name = "productId") var productId: Int,
    @ColumnInfo(name = "transactionState") var transactionState: TransactionState,
    @ColumnInfo(name = "date") var date: Date
)