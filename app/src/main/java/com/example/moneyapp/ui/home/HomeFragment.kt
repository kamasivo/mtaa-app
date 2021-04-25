package com.example.moneyapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneyapp.databinding.FragmentHomeBinding
import com.example.moneyapp.ui.BillRecyclerAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

//    private lateinit var billAdapter: BillRecyclerAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        homeViewModel.loadBills()

    }

//    private fun initRecyclerView(){
//
//        recycler_view.apply{
//            layoutManager = LinearLayoutManager(this@MainActivity)
//            addItemDecoration(topSpacingDecorator)
//            billAdapter = BillRecyclerAdapter()
//            adapter = billAdapter
//        }
//    }
}