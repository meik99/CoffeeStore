package com.rynkbit.coffeestore.customer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.rynkbit.coffeestore.NavbarHider
import com.rynkbit.coffeestore.R
import com.rynkbit.coffeestore.data.AppDatabase
import com.rynkbit.coffeestore.data.AppDatabaseStore
import com.rynkbit.coffeestore.data.entity.Customer
import com.rynkbit.coffeestore.data.entity.CustomerTransaction
import com.rynkbit.coffeestore.data.entity.Product
import com.rynkbit.coffeestore.test.CustomerGenerator
import com.rynkbit.coffeestore.test.CustomerTransactionGenerator
import com.rynkbit.coffeestore.test.ProductGenerator
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    var customers: List<Customer> = mutableListOf()
    var products: List<Product> = mutableListOf()
    var customerTransactions: List<CustomerTransaction> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavbarHider(this).hideNavbar()

        setContentView(R.layout.activity_main)

        buildDatabase()
//        generateTestEntities()

        val customerAdapter = ListCustomerAdapter()

        val displayMetrics = applicationContext.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density

        listCustomer.layoutManager = GridLayoutManager(this, (6.0 / 900 * dpWidth).toInt())
        listCustomer.adapter = customerAdapter

        val viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewmodel.customers.observe(
            this,
            Observer {
                customers = it
                customerAdapter.customers = customers
            }
        )

    }

    private fun generateTestEntities() {
        CustomerGenerator().generateCustomers()
        ProductGenerator().generateProducts()
        CustomerTransactionGenerator().generate()
    }

    private fun buildDatabase() {
        AppDatabaseStore.instance.appDatabase =
            Room.databaseBuilder(
                this,
                AppDatabase::class.java,
               "coffeestore"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}
