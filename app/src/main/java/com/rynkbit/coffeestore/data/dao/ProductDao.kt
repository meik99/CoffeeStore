package com.rynkbit.coffeestore.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rynkbit.coffeestore.data.entity.Product

@Dao
interface ProductDao: GenericDao<Product>{
    @Query("select * from product")
    override fun findAll(): LiveData<List<Product>>

    @Query("select * from product where id = :id")
    override fun findById(id: Int): LiveData<Product>

    @Insert
    override fun insertAll(vararg entity: Product)

    @Delete
    override fun delete(entity: Product)

    @Update
    override fun update(entity: Product)
}