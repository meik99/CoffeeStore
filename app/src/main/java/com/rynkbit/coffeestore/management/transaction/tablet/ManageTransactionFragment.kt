package com.rynkbit.coffeestore.management.transaction.tablet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rynkbit.coffeestore.R
import com.rynkbit.coffeestore.data.entity.Customer
import com.rynkbit.coffeestore.data.entity.CustomerTransaction
import com.rynkbit.coffeestore.data.entity.Product
import kotlinx.android.synthetic.main.fragment_manage_transaction_tablet.*
import java.text.NumberFormat
import java.text.SimpleDateFormat

class ManageTransactionFragment : Fragment() {

    companion object {
        fun newInstance() = ManageTransactionFragment()
    }

    private lateinit var viewModel: ManageTransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_manage_transaction_tablet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ManageTransactionViewModel::class.java)

        val transactionAdapter = ManageTransactionAdapter(viewModel)

        listTransactions.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        listTransactions.adapter = transactionAdapter

        viewModel.customerTransactions.observe(
            this,
            Observer {
                transactionAdapter.transactions = it

                val selectedTransaction = viewModel.selectedTransaction.value

                if(it != null && selectedTransaction != null && selectedTransaction.id > -1){
                    viewModel.selectedTransaction.value = it.single { it.id == selectedTransaction.id }
                }
            }
        )

        viewModel.customers.observe(
            this,
            Observer {
                transactionAdapter.customers = it
                UpdateCustomerTransactionTextViews()

            }
        )

        viewModel.prodcts.observe(
            this,
            Observer {
                transactionAdapter.products = it
                UpdateCustomerTransactionTextViews()
            }
        )

        viewModel.selectedTransaction.observe(
            this,
            Observer {
                val customerTransaction = it

                if(customerTransaction != null && customerTransaction.id > -1){
                    txtTransactionDate.text = SimpleDateFormat.getDateTimeInstance().format(customerTransaction.date)
                    txtTransactionStatus.text = getString(customerTransaction.transactionState.resourceStringId)

                    viewModel.selectedCustomer = viewModel.customers.value?.single { it.id == customerTransaction.customerId } ?: Customer(-1, "")
                    viewModel.selectedProduct = viewModel.prodcts.value?.single { it.id == customerTransaction.productId } ?: Product(-1, "", 0.toDouble(), 0)

                    setCustomerForTransaction(customerTransaction)
                    setOProductForTransaction(customerTransaction)
                }
            }
        )

        btnRevoke.setOnClickListener {
            if(!viewModel.revokeTransaction()){
                showTransactionErrorToast()
            }
        }

        btnClose.setOnClickListener {
            if(!viewModel.closeTransaction()){
                showTransactionErrorToast()
            }
        }

        btnOpen.setOnClickListener {
            if(!viewModel.openTransaction()){
                showTransactionErrorToast()
            }

        }
    }

    fun showTransactionErrorToast(){
        Toast.makeText(this.context, R.string.toast_error_transaction, Toast.LENGTH_SHORT).show()
    }

    private fun UpdateCustomerTransactionTextViews() {
        val customerTransaction = viewModel.selectedTransaction.value

        if (customerTransaction != null) {
            setCustomerForTransaction(customerTransaction)
            setOProductForTransaction(customerTransaction)
        }
    }

    private fun setOProductForTransaction(customerTransaction: CustomerTransaction) {
        val products = viewModel.prodcts.value
        if (products != null) {
            val product = products.singleOrNull { it.id == customerTransaction.productId }

            if (product != null) {
                txtProductName.text = product.name
                txtProductPrice.text = NumberFormat.getCurrencyInstance().format(product.price)
                txtProductStock.text = NumberFormat.getInstance().format(product.stock)
            }
        }
    }

    private fun setCustomerForTransaction(customerTransaction: CustomerTransaction) {
        val customers = viewModel.customers.value
        if (customers != null) {
            val customer = customers.singleOrNull { it.id == customerTransaction.customerId }

            if (customer != null) {
                txtCustomerName.text = customer.name
                txtCustomerBalance.text = NumberFormat.getCurrencyInstance().format(customer.balance)
            }
        }
    }

}
