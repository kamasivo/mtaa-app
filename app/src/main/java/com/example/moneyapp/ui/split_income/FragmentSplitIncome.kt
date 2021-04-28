package com.example.moneyapp.ui.split_income

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.moneyapp.GlobalApplication
import com.example.moneyapp.databinding.SplitIncomeBinding
import com.example.moneyapp.ui.add_income.Item


class SplitIncome : Fragment() {
    private lateinit var model: SplitIncomeViewModel
    private var _binding: SplitIncomeBinding? = null
    private lateinit var spinnerCategory: Spinner
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SplitIncomeBinding.inflate(inflater, container, false)
        model = ViewModelProvider(this).get(SplitIncomeViewModel::class.java)
        val view = binding.root
        model.loadCategories()

        setObservers()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        spinnerCategory = binding.incomeCategoriesSpinner
        val sum = binding.sumSplitIncome
        val create = binding.createSplitIncome

        model.splitIncomeResult.observe(viewLifecycleOwner, Observer {
            val splitIncomeResult = it ?: return@Observer

            binding.loading.visibility = View.GONE
            if (splitIncomeResult.error != null) {
                showSplitIncomeFailed(splitIncomeResult.error)
            }
            if (splitIncomeResult.success) {
                val navController = Navigation.findNavController(view)
                navController.navigate(com.example.moneyapp.R.id.action_nav_splitIncome_to_nav_home)
            }
        })

        create.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            val categoryChoose: Item = spinnerCategory.selectedItem as Item
            Log.d("categoryid", categoryChoose.id.toString())
            model.splitIncome(sum.text.toString().toInt(), categoryChoose.id)
        }
    }

    private fun setObservers() {
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
                    R.layout.simple_spinner_item,
                    items
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)

                spinnerCategory.adapter = adapter
            }
        })

    }

    private fun showSplitIncomeFailed(errorString: String) {
        Toast.makeText(this.context, errorString, Toast.LENGTH_SHORT).show()
    }
}

