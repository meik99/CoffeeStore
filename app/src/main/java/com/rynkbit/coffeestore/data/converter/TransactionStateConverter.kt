package com.rynkbit.coffeestore.data.converter

import androidx.room.TypeConverter
import com.rynkbit.coffeestore.data.entity.TransactionState

class TransactionStateConverter{
    @TypeConverter
    fun fromValue(value: Int): TransactionState {
        return TransactionState.values().single { it.ordinal == value }
    }

    @TypeConverter
    fun fromTransactionState(transactionState: TransactionState): Int {
        return transactionState.ordinal
    }
}