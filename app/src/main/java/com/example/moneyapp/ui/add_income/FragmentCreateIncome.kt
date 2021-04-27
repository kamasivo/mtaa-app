package com.example.moneyapp.ui.add_income

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.moneyapp.GlobalApplication
import com.example.moneyapp.R
import com.example.moneyapp.databinding.AddIncomeBinding


class CreateIncome : Fragment(){
    private lateinit var model: CreateIncomeViewModel
    private var _binding: AddIncomeBinding? = null
    private lateinit var spinnerCategory: Spinner
    private lateinit var spinnerBill: Spinner
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddIncomeBinding.inflate(inflater, container, false)
        model = ViewModelProvider(this).get(CreateIncomeViewModel::class.java)
        val view = binding.root
        model.loadBills()
        model.loadCategories()

        setObservers()
        return view
    }

    private fun setObservers() {
        model.listOfBills.observe(viewLifecycleOwner, Observer {
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

                spinnerBill.adapter = adapter
            }
        })

        model.listOfCategories.observe(viewLifecycleOwner, Observer {
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
        val sum = binding.sumIncome
        val loading = binding.loading
        val create = binding.createIncome
        spinnerCategory = binding.incomeSource
        spinnerBill = binding.billChoose

        val model: CreateIncomeViewModel by viewModels()

        model.createIncomeResult.observe(viewLifecycleOwner, Observer {
            val createIncomeResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (createIncomeResult.error != null) {
                showCreateIncomeFailed(createIncomeResult.error)
            }
            if (createIncomeResult.success) {
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.create_income_to_home)
            }

        })


        create.setOnClickListener {
            val billChoose: Item = spinnerBill.selectedItem as Item
            val categoryChoose: Item = spinnerCategory.selectedItem as Item
            loading.visibility = View.VISIBLE
            model.createIncome(sum.text.toString().toInt(), billChoose.id.toString().toInt(), categoryChoose.id.toString().toInt())
        }
    }

    private fun showCreateIncomeFailed(errorString: String) {
        Toast.makeText(this.context, errorString, Toast.LENGTH_SHORT).show()
    }
}

class Item (val id: Int, val name: String){
    override fun toString(): String {
        return name;
    }
}
