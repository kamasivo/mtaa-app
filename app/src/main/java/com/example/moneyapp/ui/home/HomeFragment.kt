package com.example.moneyapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moneyapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val view = binding.root
        val adapter = BillAdapter()
        binding.billsRecycler.adapter = adapter
        homeViewModel.listOfBills.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
                Log.d("HomeFragment", adapter.data.toString())
            }
        })
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        homeViewModel.loadBills()

    }
}