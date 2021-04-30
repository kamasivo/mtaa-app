package com.example.moneyapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moneyapp.api.models.Bill
import com.example.moneyapp.databinding.HomeRecyclerBinding

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: HomeRecyclerBinding? = null
    private val binding get() = _binding!!
    val adapter = BillAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = HomeRecyclerBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val view = binding.root
        homeViewModel.bills.observe(viewLifecycleOwner, Observer<List<Bill>> { bills ->
            bills?.apply {
                binding.billsRecycler.adapter = adapter
                adapter.data = bills
            }
        })
        homeViewModel.loadBills()
        return view
    }

}