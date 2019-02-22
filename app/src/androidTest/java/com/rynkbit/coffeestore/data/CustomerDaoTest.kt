package com.rynkbit.coffeestore.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.rynkbit.coffeestore.data.entity.Customer
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class CustomerDaoTest{
    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun initializeDatabase (){
        val db = buildDatabase()
        db.close()
    }

    @Test
    fun insertCustomer(){
        val customer1 = Customer(0, "Test 1")
        val customer2 = Customer(0, "Test 2")
        val db = buildDatabase()

        db.customerDao().insertAll(customer1, customer2)

        val customersLivedata = db.customerDao().findAll()

        assertNotNull(customersLivedata)
        var customers: MutableList<Customer> = mutableListOf()

        customersLivedata.observeForever {
            customers.addAll(it)
        }

        assertNotNull(customers)
        assertNotNull(customers[0])
        assertNotNull(customers[1])
        assertNotEquals(customers[0].id, customers[1].id)

        assertNotNull(customers.find { it.name == customer1.name })
        assertNotNull(customers.find { it.name == customer2.name })
    }

    @Test
    fun deleteCustomer(){
        val customer1 = Customer(0, "Test 1")
        val customer2 = Customer(0, "Test 2")
        val db = buildDatabase()

        db.customerDao().insertAll(customer1, customer2)
        db.customerDao().delete(Customer(1, ""))
        db.customerDao().findAll().observeForever {
            assertNotNull(it)
            assertEquals(it.size, 1)
        }
    }

    fun buildDatabase(): AppDatabase {
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(
            appContext,
            AppDatabase::class.java
        ).build()

        assertNotNull(db)

        return db
    }
}