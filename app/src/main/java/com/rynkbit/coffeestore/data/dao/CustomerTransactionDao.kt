package com.rynkbit.coffeestore.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rynkbit.coffeestore.data.entity.CustomerTransaction
import com.rynkbit.coffeestore.data.entity.TransactionState

@Dao
interface CustomerTransactionDao: GenericDao<CustomerTransaction>{
    @Query("select * from customertransaction order by date desc")
    override fun findAll(): LiveData<List<CustomerTransaction>>

    @Query("select * from customertransaction where id = :id order by date desc")
    override fun findById(id: Int): LiveData<CustomerTransaction>

    @Query("select * from customertransaction where customerId = :customerId order by date desc")
    fun findByCustomerId(customerId: Int): LiveData<List<CustomerTransaction>>

    @Query("select * from customertransaction where date >= :fromDate and date <= :toDate order by date desc")
    fun findByDateSpan(fromDate: Long, toDate: Long): LiveData<List<CustomerTransaction>>

    @Insert
    override fun insertAll(vararg entity: CustomerTransaction)

    @Delete
    override fun delete(entity: CustomerTransaction)

    @Update
    override fun update(entity: CustomerTransaction)

    @Query("update customertransaction set transactionState = :transactionState where customerId = :customerId and transactionState != 2")
    fun setTransactionStateByCustomerId(customerId: Int, transactionState: TransactionState)
}