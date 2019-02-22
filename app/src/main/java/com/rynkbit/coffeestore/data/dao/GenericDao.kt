package com.rynkbit.coffeestore.data.dao

import androidx.lifecycle.LiveData
import com.rynkbit.coffeestore.data.entity.Customer

interface GenericDao<T>{
    fun findAll(): LiveData<List<T>>
    fun insertAll(vararg entity: T)
    fun delete(entity: T)
    fun update(entity: T)
    fun findById(id: Int): LiveData<T>
}