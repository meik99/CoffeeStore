package com.rynkbit.coffeestore.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rynkbit.coffeestore.data.entity.Customer

@Dao
interface CustomerDao: GenericDao<Customer>{
    @Query("select * from customer")
    override fun findAll(): LiveData<List<Customer>>

    @Query("select * from customer where id = :id")
    override fun findById(id: Int): LiveData<Customer>

//    @Query("update customer set balance = (select balance from customer where id = :customerId) + (select price from product where id = :productId) where id = :customerId")
//    fun addProductToBalance(customerId: Int, productId: Int)
//
//    @Query("update customer set balance = (select balance from customer where id = :customerId) - (select price from product where id = :productId) where id = :customerId")
//    fun removeProductFromBalance(customerId: Int, productId: Int)

    @Insert
    override fun insertAll(vararg entity: Customer)

    @Delete
    override fun delete(entity: Customer)

    @Update
    override fun update(entity: Customer)

}