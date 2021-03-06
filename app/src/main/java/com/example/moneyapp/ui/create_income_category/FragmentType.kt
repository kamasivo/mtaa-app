package com.example.moneyapp.ui.create_income_category

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.moneyapp.R
import com.example.moneyapp.api.models.Category
import com.example.moneyapp.databinding.CreateIncomeCategoryBinding


class Type : Fragment() {
    private lateinit var newTypeViewModel: NewTypeViewModel
    val adapter = IncomeCategoryAdapter()

    private var _binding: CreateIncomeCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateIncomeCategoryBinding.inflate(inflater, container, false)
        newTypeViewModel = ViewModelProvider(this).get(NewTypeViewModel::class.java)
        val view = binding.root

        newTypeViewModel.incomeCategories.observe(viewLifecycleOwner, Observer<List<Category>> { incomeCategories ->
            incomeCategories?.apply {
                binding.incomeCategoriesRecycler.adapter = adapter
                adapter.data = incomeCategories
            }
        })
        newTypeViewModel.loadIncomeCategories()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val name = binding.nameType
        val loading = binding.loading
        val create = binding.createType


        val model: NewTypeViewModel by viewModels()


        model.newTypeFormState.observe(viewLifecycleOwner, Observer {
            val newTypeState = it ?: return@Observer

            // disable login button unless both username / password is valid
            create.isEnabled = newTypeState.isDataValid


            create.isEnabled = newTypeState.isDataValid

            if (newTypeState.nameError != null) {
                name.error = getString(newTypeState.nameError)
            }

        })

        model.newTypeResult.observe(viewLifecycleOwner, Observer {
            val newTypeResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (newTypeResult.error != null) {
                showNewTypeFailed(newTypeResult.error)
            }
            if (newTypeResult.success) {
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.action_type)
            }

        })

        name.afterTextChanged {
            model.newTypeDataChanged(
                    name.text.toString()
            )
        }


        create.setOnClickListener {
            loading.visibility = View.VISIBLE
            model.newType(name.text.toString())
        }
//        }
    }




    private fun showNewTypeFailed(errorString: String) {
        Toast.makeText(this.context, errorString, Toast.LENGTH_SHORT).show()
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}


