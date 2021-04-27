package com.example.moneyapp.ui.category

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.moneyapp.R
import com.example.moneyapp.databinding.FragmentCategoryBinding


class Category : Fragment() {
    private var _binding: FragmentCategoryBinding? = null

    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
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

            // disable login button unless both username / password is valid
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
            }

        })

        name.afterTextChanged {
            model.newCategoryDataChanged(
                    name.text.toString()
            )
        }


        create.setOnClickListener {
            loading.visibility = View.VISIBLE
            model.newCategory(name.text.toString())
        }
//        }
    }

    private fun showNewCategoryFailed(errorString: String) {
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
