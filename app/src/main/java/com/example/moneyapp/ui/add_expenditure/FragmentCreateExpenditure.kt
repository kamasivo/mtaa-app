package com.example.moneyapp.ui.add_expenditure

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.moneyapp.GlobalApplication
import com.example.moneyapp.R
import com.example.moneyapp.databinding.AddExpenditureBinding
import com.example.moneyapp.ui.add_income.Item


class CreateExpenditure : Fragment() {
    private lateinit var model: CreateExpenditureViewModel
    private var _binding: AddExpenditureBinding? = null
    private lateinit var spinnerCategory: Spinner
    private lateinit var spinnerBill: Spinner
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddExpenditureBinding.inflate(inflater, container, false)
        model = ViewModelProvider(this).get(CreateExpenditureViewModel::class.java)
        val view = binding.root
        model.loadBills()
        model.loadCategories()
        setObservers()
        return view
    }

    private fun setObservers() {
        model.bills.observe(viewLifecycleOwner, Observer {
            it?.let {
                val items: ArrayList<Item> = ArrayList()

                for (i in it) {
                    Log.d("FragmentExpenditure", i.name.toString())
                    val it = Item((i.id.toString().toInt()), i.name.toString())
                    items.add(it)
                }

                val adapter: ArrayAdapter<Item> = ArrayAdapter<Item>(
                        GlobalApplication.appContext!!,
                        android.R.layout.simple_spinner_item,
                        items
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)

                spinnerBill.adapter = adapter
            }
        })

        model.expenditureCategories.observe(viewLifecycleOwner, Observer {
            it?.let {
                val items: ArrayList<Item> = ArrayList()

                for (i in it) {
                    Log.d("FragmentCreateIncome", i.name.toString())
                    val it = Item((i.id.toString().toInt()), i.name.toString())
                    items.add(it)
                }

                val adapter: ArrayAdapter<Item> = ArrayAdapter<Item>(
                        GlobalApplication.appContext!!,
                        android.R.layout.simple_spinner_item,
                        items
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)

                spinnerCategory.adapter = adapter
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sum = binding.sumExpenditure
        val loading = binding.loading
        val create = binding.createExpenditure
        spinnerCategory = binding.incomeSource
        spinnerBill = binding.billChoose

        val model: CreateExpenditureViewModel by viewModels()

        model.createExpenditureResult.observe(viewLifecycleOwner, Observer {
            val createExpenditureResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (createExpenditureResult.error != null) {
                showCreateExpenditureFailed(createExpenditureResult.error)
            }
            if (createExpenditureResult.success) {
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.action_nav_createExpenditure_to_nav_home)
            }

        })

        create.setOnClickListener {
            if(spinnerBill.selectedItem != null && spinnerCategory.selectedItem != null) {
                val billChoose: Item = spinnerBill.selectedItem as Item
                val categoryChoose: Item = spinnerCategory.selectedItem as Item
                loading.visibility = View.VISIBLE
                model.createExpenditure(sum.text.toString().toInt(), billChoose.id.toString().toInt(), categoryChoose.id.toString().toInt())
            }
        }
//        }
    }

    private fun showCreateExpenditureFailed(errorString: String) {
        Toast.makeText(this.context, errorString, Toast.LENGTH_SHORT).show()
    }
}

