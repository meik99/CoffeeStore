package com.rynkbit.coffeestore.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rynkbit.coffeestore.data.converter.DateConverter
import com.rynkbit.coffeestore.data.converter.TransactionStateConverter
import com.rynkbit.coffeestore.data.dao.CustomerDao
import com.rynkbit.coffeestore.data.dao.CustomerTransactionDao
import com.rynkbit.coffeestore.data.dao.ProductDao
import com.rynkbit.coffeestore.data.entity.Customer
import com.rynkbit.coffeestore.data.entity.CustomerTransaction
import com.rynkbit.coffeestore.data.entity.Product

@Database(entities = [Customer::class, Product::class, CustomerTransaction::class], version = 5)
@TypeConverters(TransactionStateConverter::class, DateConverter::class)
abstract class AppDatabase: RoomDatabase(){
    abstract fun customerDao(): CustomerDao
    abstract fun productDao(): ProductDao
    abstract fun customerTransactionDao(): CustomerTransactionDao
}