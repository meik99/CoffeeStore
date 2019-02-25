package com.rynkbit.coffeestore.export

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.MutableLiveData
import com.rynkbit.coffeestore.R
import com.rynkbit.coffeestore.data.entity.Customer
import com.rynkbit.coffeestore.data.entity.CustomerTransaction
import com.rynkbit.coffeestore.data.entity.Product
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class ExportDataService: IntentService(ExportDataService::class.java.simpleName){
    private var binder: Binder? = null

    companion object {
        val CHANNEL_ID = "WRTING FILE"
        val NOTIFICATION_ID = 5
        val ERROR_NO_STORAGE = 5
        val ERROR_CREATING_DIRECTORY = 10
        val ERROR_WAITING_FOR_DATA = 15
    }

    val customerTransactions = mutableListOf<CustomerTransaction>()
    val products = mutableListOf<Product>()
    val customer = mutableListOf<Customer>()

    override fun onHandleIntent(intent: Intent?) {
        waitForTransactions()
        waitForCustomerAndProductDependecies()

        val (stringBuilder, simpleDateFormat) =
            transformDataToCSVString()

        checkStorageAvailable()

        val directory =
            createExportDirectory()

        writeCSVStringToFile(directory, simpleDateFormat, stringBuilder)
    }

    private fun writeCSVStringToFile(
        directory: File,
        simpleDateFormat: SimpleDateFormat,
        stringBuilder: StringBuilder
    ) {
        val file = File(
            directory,
            simpleDateFormat.format(customerTransactions.first().date) + "_" + simpleDateFormat.format(
                customerTransactions.last().date
            ) + ".csv"
        )
        val bufferedWriter = BufferedWriter(FileWriter(file))

        bufferedWriter.write(stringBuilder.toString())
        bufferedWriter.flush()
        bufferedWriter.close()

        NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID)
        binder?.finishedSuccessful?.postValue(true)
        stopSelf()
    }

    private fun createExportDirectory(): File {
        val directory =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "CoffeeShop")

        if (!directory.mkdirs()) {
            binder?.errorCode?.postValue(ERROR_CREATING_DIRECTORY)
            stopSelf()
        }
        return directory
    }

    private fun checkStorageAvailable() {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            binder?.errorCode?.postValue(ERROR_NO_STORAGE)
            stopSelf()
        }
    }

    private fun transformDataToCSVString(): Pair<StringBuilder, SimpleDateFormat> {
        val stringBuilder =
            StringBuilder("TRANSACTION_ID,TRANSACTION_DATE,TRANSACTION_STATE,CUSTOMER_ID,CUSTOMER,BALANCE,PRODUCT_ID,PRODUCT,PRICE,STOCK\n")
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        customerTransactions.forEach { transaction ->
            val product = products.single { product -> product.id == transaction.productId }
            val customer = customer.single { customer -> customer.id == transaction.customerId }

            stringBuilder
                .append(transaction.id)
                .append(",")
                .append(simpleDateFormat.format(transaction.date))
                .append(",")
                .append(transaction.transactionState.name)
                .append(",")
                .append(customer.id)
                .append(",")
                .append(customer.name)
                .append(",")
                .append(customer.balance)
                .append(",")
                .append(product.id)
                .append(",")
                .append(product.name)
                .append(",")
                .append(product.price)
                .append(",")
                .append(product.stock)
                .append("\n")
        }
        return Pair(stringBuilder, simpleDateFormat)
    }

    private fun waitForCustomerAndProductDependecies() {
        var allProductsFound: Boolean
        var allCustomersFound: Boolean

        do {
            allProductsFound = true
            allCustomersFound = true

            customerTransactions.forEach {
                if (products.filter { product -> product.id == it.productId }.isEmpty()) {
                    allProductsFound = false
                }
                if (customer.filter { customer -> customer.id == it.customerId }.isEmpty()) {
                    allCustomersFound = false
                }
            }
        } while (!allCustomersFound || !allProductsFound)
    }

    private fun waitForTransactions() {
        var waitCounter = 0
        val waitMax = 10

        do {
            Thread.sleep(1000)
            waitCounter++
        } while (customerTransactions.isEmpty() && waitCounter < waitMax)

        if (customerTransactions.isEmpty()) {
            binder?.errorCode?.postValue(ERROR_WAITING_FOR_DATA)
            stopSelf()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        createNotificationChannel()
        val builder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentTitle("Writing to file")
            setContentText("")
            setSmallIcon(R.drawable.ic_file_download_black_24dp)
            setPriority(NotificationCompat.PRIORITY_LOW)
        }
        NotificationManagerCompat.from(this).apply {
            builder.setProgress(0, 0, true)
            notify(NOTIFICATION_ID, builder.build())
        }

        return Service.START_STICKY
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_write_file)
            val descriptionText = getString(R.string.channel_write_file_description)
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        if(binder == null) {
            binder = Binder(this)
        }

        return binder
    }

    class Binder(val exportDataService: ExportDataService) : android.os.Binder() {
        val errorCode: MutableLiveData<Int> = MutableLiveData()
        val finishedSuccessful: MutableLiveData<Boolean> = MutableLiveData()
    }

}
