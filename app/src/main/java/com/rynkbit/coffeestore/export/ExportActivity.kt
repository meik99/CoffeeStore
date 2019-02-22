package com.rynkbit.coffeestore.export

import android.Manifest
import android.app.DatePickerDialog
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.ViewModelProviders
import com.rynkbit.coffeestore.NavbarHider
import com.rynkbit.coffeestore.R
import kotlinx.android.synthetic.main.activity_export.*
import java.text.SimpleDateFormat
import java.util.*

class ExportActivity : AppCompatActivity() {

    lateinit var viewModel: ExportViewModel
    var binder: ExportDataService.Binder? = null
    var serviceConnection: ServiceConnection? = null

    companion object {
        private val REQUEST_FOREGROUND_SERIVCE_PERMISSION: Int = 5
        private val REQUEST_READ_FILE_PERMISSION: Int = 10
        private val REQUEST_WRITE_FILE_PERMISSION: Int = 15
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavbarHider(this).hideNavbar()
        setContentView(R.layout.activity_export)

        viewModel = ViewModelProviders.of(this).get(ExportViewModel::class.java)

        val currentDateCalendar = Calendar.getInstance(Locale.getDefault())

        editFromDate.setOnClickListener {
            DatePickerDialog(
                this, object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        viewModel.fromDate = GregorianCalendar(year, month, dayOfMonth).time
                        editFromDate.setText(
                            SimpleDateFormat(
                                "dd.MM.yyyy",
                                Locale.getDefault()
                            ).format(viewModel.fromDate)
                        )
                    }
                },
                currentDateCalendar.get(Calendar.YEAR),
                currentDateCalendar.get(Calendar.MONTH),
                currentDateCalendar.get(Calendar.DAY_OF_MONTH)
            )
                .show()
        }
        editToDate.setOnClickListener {
            DatePickerDialog(
                this, object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        viewModel.toDate = GregorianCalendar(year, month, dayOfMonth).time
                        editToDate.setText(SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(viewModel.toDate))
                    }
                },
                currentDateCalendar.get(Calendar.YEAR),
                currentDateCalendar.get(Calendar.MONTH),
                currentDateCalendar.get(Calendar.DAY_OF_MONTH)
            )
                .show()
        }

        btnExport.setOnClickListener {
            if(viewModel.fromDate != null && viewModel.toDate != null && viewModel.fromDate!!.before(viewModel.toDate!!)) {
                if (PermissionChecker.checkSelfPermission(
                        this,
                        Manifest.permission.FOREGROUND_SERVICE
                    ) == PermissionChecker.PERMISSION_DENIED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf("android.permission.FOREGROUND_SERVICE"),
                        REQUEST_FOREGROUND_SERIVCE_PERMISSION
                    )
                } else {
                    requestReadFilePermissions()
                }
            }else{
                Toast.makeText(this,
                    getString(R.string.please_check_dates),
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_FOREGROUND_SERIVCE_PERMISSION -> {
                if(grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                    requestReadFilePermissions()
                }
            }
            REQUEST_READ_FILE_PERMISSION -> {
                if(grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                    requestWriteFilePermissions()
                }
            }
            REQUEST_WRITE_FILE_PERMISSION -> {
                if(grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                    startWriteFileService()
                }
            }
        }
    }

    override fun onStop() {
        if(serviceConnection != null){
            unbindService(serviceConnection)
        }

        super.onStop()
    }

    private fun requestReadFilePermissions() {
        if (PermissionChecker.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PermissionChecker.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf("android.permission.WRITE_EXTERNAL_STORAGE"),
                REQUEST_READ_FILE_PERMISSION
            )
        } else {
            requestWriteFilePermissions()
        }
    }

    private fun requestWriteFilePermissions() {
        if (PermissionChecker.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PermissionChecker.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf("android.permission.READ_EXTERNAL_STORAGE"),
                REQUEST_WRITE_FILE_PERMISSION
            )
        } else {
            startWriteFileService()
        }
    }

    private fun startWriteFileService() {
        viewModel.customers.observe(
            this,
            androidx.lifecycle.Observer {
                if(binder != null){
                    binder?.exportDataService?.customer?.clear()
                    binder?.exportDataService?.customer?.addAll(it)
                }
            }
        )
        viewModel.products.observe(
            this,
            androidx.lifecycle.Observer {
                if(binder != null){
                    binder?.exportDataService?.products?.clear()
                    binder?.exportDataService?.products?.addAll(it)
                }
            }
        )

        viewModel.findByDateSpan().observe(
            this,
            androidx.lifecycle.Observer {
                if(binder == null && it.isNotEmpty()) {
                    Intent(this, ExportDataService::class.java).also { intent ->
                        serviceConnection = object : ServiceConnection {
                            override fun onServiceDisconnected(name: ComponentName?) {

                            }

                            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                                binder = service as ExportDataService.Binder
                                binder?.exportDataService?.customerTransactions?.addAll(it)

                                binder?.errorCode?.observe(
                                    this@ExportActivity,
                                    androidx.lifecycle.Observer {
                                        print(it)
                                    }
                                )
                                binder?.finishedSuccessful?.observe(
                                    this@ExportActivity,
                                    androidx.lifecycle.Observer {
                                        if(it != null && it == true){
                                            Toast.makeText(this@ExportActivity, getString(R.string.sucessfully_exported_data), Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                )

                                val products = viewModel.products.value
                                val customer = viewModel.customers.value

                                if(products != null){
                                    binder?.exportDataService?.products?.clear()
                                    binder?.exportDataService?.products?.addAll(products)
                                }

                                if(customer != null){
                                    binder?.exportDataService?.customer?.clear()
                                    binder?.exportDataService?.customer?.addAll(customer)
                                }
                            }

                        }
                        bindService(intent, serviceConnection, 0)
                        startService(intent)
                    }
                }else if(it.isNotEmpty()){
                    binder?.exportDataService?.customerTransactions?.clear()
                    binder?.exportDataService?.customerTransactions?.addAll(it)
                }
            }
        )
    }
}
