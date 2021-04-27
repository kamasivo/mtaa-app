package com.example.moneyapp.ui.split_income

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
import com.example.moneyapp.databinding.SplitIncomeBinding


class SplitIncome : Fragment() {
    private var _binding: SplitIncomeBinding? = null

    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SplitIncomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sum = binding.sumSplitIncome
        val loading = binding.loading
        val create = binding.createSplitIncome


        val model: SplitIncomeViewModel by viewModels()

//
//        model.splitIncomeFormState.observe(viewLifecycleOwner, Observer {
//            val splitIncomeState = it ?: return@Observer
//
//            // disable login button unless both username / password is valid
//            create.isEnabled = splitIncomeState.isDataValid
//
//
//            create.isEnabled = splitIncomeState.isDataValid
//
//            if (splitIncomeState.sumError != null) {
//                sum.error = getString(splitIncomeState.sumError)
//            }
//
//        })

        model.splitIncomeResult.observe(viewLifecycleOwner, Observer {
            val splitIncomeResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (splitIncomeResult.error != null) {
                showSplitIncomeFailed(splitIncomeResult.error)
            }
            if (splitIncomeResult.success) {
            }

        })

//        name.afterTextChanged {
//            model.splitIncomeDataChanged(
//                    name.text.toString()
//            )
//        }


        create.setOnClickListener {
            loading.visibility = View.VISIBLE
//            model.splitIncome(name.text.toString())
        }
//        }
    }

    private fun showSplitIncomeFailed(errorString: String) {
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
