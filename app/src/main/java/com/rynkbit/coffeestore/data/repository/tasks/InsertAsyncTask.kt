package com.rynkbit.coffeestore.data.repository.tasks

import android.os.AsyncTask
import com.rynkbit.coffeestore.data.dao.GenericDao

class InsertAsyncTask<T>(private val dao: GenericDao<T>) : AsyncTask<T, Void, Boolean>(){
    override fun doInBackground(vararg params: T): Boolean {
        dao.insertAll(*params)
        return true
    }
}