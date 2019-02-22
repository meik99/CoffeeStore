package com.rynkbit.coffeestore.management.customer.phone

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rynkbit.coffeestore.R

class ManageCustomersFragment : Fragment() {

    companion object {
        fun newInstance() = ManageCustomersFragment()
    }

    private lateinit var viewModel: ManageCustomersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_manage_customers_phone, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ManageCustomersViewModel::class.java)


    }

}
