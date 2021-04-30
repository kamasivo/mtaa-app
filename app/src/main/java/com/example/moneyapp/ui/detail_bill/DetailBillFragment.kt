package com.example.moneyapp.ui.detail_bill

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.moneyapp.R
import com.example.moneyapp.databinding.DetailBillBinding

class DetailBillFragment : Fragment() {
    private lateinit var model: DetailBillViewModel
    private var _binding: DetailBillBinding? = null
    private val binding get() = _binding!!
    val args: DetailBillFragmentArgs by navArgs()
    val adapter = ExpenditureAdapter()
    val incomeAdapter = IncomeAdapter()




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

        val name = binding.billName
        val incomePercents = binding.billPercents
        val description = binding.billDescription
        val sum = binding.billSum
        val loading = binding.loading
        val create = binding.billUpdate

        model.newBillResult.observe(viewLifecycleOwner, Observer {
            val newBillResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (newBillResult.error != null) {
                showNewBillFailed(newBillResult.error)
            }
            if (newBillResult.success) {
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.nav_home)
            }

        })


        create.setOnClickListener {
            var incPercent = 0
            var totalSum = 0
            try {
                incPercent = incomePercents.text.toString().toInt()
            } catch (e: NumberFormatException) {
                // handle the exception
                incPercent = 0
            }
            try {
                totalSum = sum.text.toString().toInt()
            } catch (e: NumberFormatException) {
                // handle the exception
                totalSum = 0
            }
            loading.visibility = View.VISIBLE
            model.updateBill(name.text.toString(), incPercent, description.text.toString(), totalSum, args.billId)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DetailBillBinding.inflate(inflater, container, false)
        model = ViewModelProvider(this).get(DetailBillViewModel::class.java)
        val view = binding.root

        model.listOfExpenditures.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("detailbillfragment", it.toString())
                binding.expenditureRecycler.adapter = adapter
                adapter.data = it
            }
        })
        model.loadExpenditures(args.billId)

        model.listOfIncomes.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("detailbillfragment", it.toString())
                binding.incomeRecycler.adapter = incomeAdapter
                incomeAdapter.data = it
            }
        })
        model.loadIncomes(args.billId)

        return view
    }
    private fun showNewBillFailed(errorString: String) {
        Toast.makeText(this.context, errorString, Toast.LENGTH_SHORT).show()
    }
}






