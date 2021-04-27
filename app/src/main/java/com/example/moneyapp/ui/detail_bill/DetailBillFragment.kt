package com.example.moneyapp.ui.detail_bill

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.moneyapp.databinding.FragmentBillBinding

class DetailBillFragment : Fragment() {
    private lateinit var model: DetailBillViewModel
    private var _binding: FragmentBillBinding? = null
    private val binding get() = _binding!!
    val args: DetailBillFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBillBinding.inflate(inflater, container, false)
        model = ViewModelProvider(this).get(DetailBillViewModel::class.java)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        model.load(args.billId)
        model.bill.observe(viewLifecycleOwner, Observer {
            val bill = it ?: return@Observer
            Log.d("DetailBillFragment",bill.incomePercents.toString())
            binding.billName.setText(bill.name)
            binding.billPercents.setText(bill.incomePercents!!.toString())
            binding.billDescription.setText(bill.description)
            binding.billSum.setText(bill.sum!!.toString())
        })
    }
}