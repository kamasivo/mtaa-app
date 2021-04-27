package com.example.moneyapp.ui.type

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.moneyapp.R
import com.example.moneyapp.databinding.FragmentTypeBinding


class Type : Fragment() {
    private var _binding: FragmentTypeBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTypeBinding.inflate(inflater, container, false)
        val view = binding.root
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


