package com.example.moneyapp.ui.category

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
import com.example.moneyapp.api.models.ExpenditureCategory
import com.example.moneyapp.databinding.CreateExpenditureCategoryBinding
import com.example.moneyapp.ui.add_income.Item


class Category : Fragment() {

    val adapter = ExpenditureCategoryAdapter()
    private lateinit var model: NewCategoryViewModel
    private var _binding: CreateExpenditureCategoryBinding? = null
    private lateinit var spinnerBill: Spinner
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateExpenditureCategoryBinding.inflate(inflater, container, false)
        model = ViewModelProvider(this).get(NewCategoryViewModel::class.java)
        val view = binding.root
        model.loadBills()

        model.expenditureCategories.observe(viewLifecycleOwner, Observer<List<ExpenditureCategory>> { expenditureCategories ->
            expenditureCategories?.apply {
                binding.expenditureCategoriesRecycler.adapter = adapter
                adapter.data = expenditureCategories
            }
        })
        model.loadExpenditureCategories()

        setObservers()
        return view
    }

    private fun setObservers() {
        model.listOfBills.observe(viewLifecycleOwner, Observer {
            it?.let {
                val items: ArrayList<Item> = ArrayList()

                for (i in it) {
                    Log.d("FragmentCategory", i.name.toString())
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
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val name = binding.nameCategory
        val loading = binding.loading
        val create = binding.createCategory
        spinnerBill = binding.billChoose


        val model: NewCategoryViewModel by viewModels()


        model.newCategoryResult.observe(viewLifecycleOwner, Observer {
            val newCategoryResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (newCategoryResult.error != null) {
                showNewCategoryFailed(newCategoryResult.error)
            }
            if (newCategoryResult.success) {
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.action_category)
            }

        })

        create.setOnClickListener {
            val billChoose: Item = spinnerBill.selectedItem as Item
            loading.visibility = View.VISIBLE
            model.newCategory(name.text.toString(), billChoose.id.toString().toInt())
        }
//        }
    }

    private fun showNewCategoryFailed(errorString: String) {
        Toast.makeText(this.context, errorString, Toast.LENGTH_SHORT).show()
    }
}


