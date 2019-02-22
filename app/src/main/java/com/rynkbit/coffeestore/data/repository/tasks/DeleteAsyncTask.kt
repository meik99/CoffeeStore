package com.rynkbit.coffeestore.data.repository.tasks

import android.os.AsyncTask
import com.rynkbit.coffeestore.data.dao.GenericDao

class DeleteAsyncTask<T>(private val dao: GenericDao<T>) : AsyncTask<T, Void, Boolean>(){
    override fun doInBackground(vararg params: T): Boolean {
        for(param in params){
            dao.delete(param)
        }
        return true
    }
}