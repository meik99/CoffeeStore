package com.rynkbit.coffeestore.data

class AppDatabaseStore private constructor() {
    companion object {
        val instance = AppDatabaseStore()
    }

    lateinit var appDatabase: AppDatabase
}