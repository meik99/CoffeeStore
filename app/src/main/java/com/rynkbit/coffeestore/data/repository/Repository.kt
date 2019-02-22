package com.rynkbit.coffeestore.data.repository

import androidx.lifecycle.LiveData
import com.rynkbit.coffeestore.data.dao.GenericDao
import com.rynkbit.coffeestore.data.repository.tasks.DeleteAsyncTask
import com.rynkbit.coffeestore.data.repository.tasks.InsertAsyncTask
import com.rynkbit.coffeestore.data.repository.tasks.UpdateAsyncTask

abstract class Repository<T> {
    val dao: GenericDao<T>

    constructor(dao: GenericDao<T>){
        this.dao = dao
    }

    open fun insert(vararg entites: T){
        InsertAsyncTask(dao).execute(*entites)
    }

    open fun delete(vararg entites: T){
        DeleteAsyncTask(dao).execute(*entites)
    }

    open fun findById(id: Int): LiveData<T> {
        return dao.findById(id)
    }

    open fun update(vararg entities: T){
        UpdateAsyncTask(dao).execute(*entities)
    }
}