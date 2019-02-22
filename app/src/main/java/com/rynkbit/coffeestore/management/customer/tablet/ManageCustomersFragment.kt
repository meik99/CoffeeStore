package com.rynkbit.coffeestore.management.customer.tablet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rynkbit.coffeestore.R
import com.rynkbit.coffeestore.data.entity.Customer
import com.rynkbit.coffeestore.toInternationalDouble
import kotlinx.android.synthetic.main.fragment_manage_customers_tablet.*
import java.text.NumberFormat

class ManageCustomersFragment : Fragment() {

    companion object {
        fun newInstance() = ManageCustomersFragment()
    }

    private lateinit var viewModel: ManageCustomersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_manage_customers_tablet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ManageCustomersViewModel::class.java)

        val customerAdapter = ManageCustomerAdapter(viewModel)
        listCustomers.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        listCustomers.adapter = customerAdapter

        viewModel.selectedCustomer.observe(
            this,
            Observer {
                btnDelete.isEnabled = it.id > -1
                btnClearBalance.isEnabled = it.id > -1
                btnSave.isEnabled = it.id > -1

                if(it.id > 0){
                    editCustomerName.setText(it.name, TextView.BufferType.EDITABLE)
                    editCustomerBalance.setText(
                        NumberFormat.getInstance().format(it.balance), TextView.BufferType.EDITABLE)
                }
            }
        )

        viewModel.customers.observe(
            this,
            Observer {
                if(it.isNotEmpty()){
                    listCustomers.visibility = View.VISIBLE
                    txtNoCustomersFound.visibility = View.GONE

                    if(viewModel.selectedCustomer.value?.id ?: -1 > -1){
                        val selectedCustomer = viewModel.selectedCustomer.value

                        if(selectedCustomer != null) {
                            var customer =
                                it.singleOrNull { customer -> customer.id == selectedCustomer.id }

                            if(customer == null){
                                customer = Customer(-1, "")
                            }

                            viewModel.selectedCustomer.value = customer

                        }
                    }
                }else{
                    listCustomers.visibility = View.GONE
                    txtNoCustomersFound.visibility = View.VISIBLE
                }
                customerAdapter.customers = it
            }
        )

        fabAddCustomer.setOnClickListener {
            if(editCustomerName.text.isNotBlank() && editCustomerBalance.text.isNotBlank()) {
                viewModel.insertCustomer(
                    Customer(
                        0,
                        editCustomerName.text.toString(),
                        editCustomerBalance.text.toString().toInternationalDouble()
                    )
                )
                editCustomerName.setText(String(), TextView.BufferType.EDITABLE)
                editCustomerBalance.setText(String(), TextView.BufferType.EDITABLE)
            }
        }

        btnSave.setOnClickListener {
            val customer = viewModel.selectedCustomer.value

            if(editCustomerName.text.isNotBlank() && editCustomerBalance.text.isNotBlank() && customer != null) {
                customer.name = editCustomerName.text.toString()
                customer.balance = editCustomerBalance.text.toString().toInternationalDouble()
                viewModel.updateCustomer(customer)
            }
        }

        btnDelete.setOnClickListener(DeleteCustomerOnClickListener(viewModel))

        btnClearBalance.setOnClickListener {
            viewModel.clearBalance()

            Toast
                .makeText(it.context, R.string.toast_balance_cleared, Toast.LENGTH_SHORT)
                .show()
        }
    }
}
