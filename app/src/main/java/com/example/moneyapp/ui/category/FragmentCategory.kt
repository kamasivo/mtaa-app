package com.example.moneyapp.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.moneyapp.R
import com.example.moneyapp.databinding.CreateExpenditureCategoryBinding


class Category : Fragment() {
    private var _binding: CreateExpenditureCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateExpenditureCategoryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val name = binding.nameCategory
        val loading = binding.loading
        val create = binding.createCategory


        val model: NewCategoryViewModel by viewModels()


        model.newCategoryFormState.observe(viewLifecycleOwner, Observer {
            val newCategoryState = it ?: return@Observer

            create.isEnabled = newCategoryState.isDataValid


            create.isEnabled = newCategoryState.isDataValid

            if (newCategoryState.nameError != null) {
                name.error = getString(newCategoryState.nameError)
            }

        })

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
            loading.visibility = View.VISIBLE
//            model.newCategory(name.text.toString()) // todo
        }
//        }
    }

    private fun showNewCategoryFailed(errorString: String) {
        Toast.makeText(this.context, errorString, Toast.LENGTH_SHORT).show()
    }
}
