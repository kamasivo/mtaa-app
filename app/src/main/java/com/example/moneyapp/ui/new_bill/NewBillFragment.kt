package com.example.moneyapp.ui.newbill

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
import androidx.navigation.Navigation
import com.example.moneyapp.R
import com.example.moneyapp.databinding.FragmentNewBillBinding


class NewBillFragment : Fragment() {
    private var _binding: FragmentNewBillBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewBillBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val name = binding.name
        val incomePercents = binding.incomePercents
        val description = binding.description
        val sum = binding.sum
        val loading = binding.loading
        val create = binding.create


        val model: NewBillViewModel by viewModels()


        model.newBillFormState.observe(viewLifecycleOwner, Observer {
            val newBillState = it ?: return@Observer

            // disable login button unless both username / password is valid
            create.isEnabled = newBillState.isDataValid


            create.isEnabled = newBillState.isDataValid

            if (newBillState.nameError != null) {
                name.error = getString(newBillState.nameError)
            }
            if (newBillState.incomePercentsError != null) {
                incomePercents.error = getString(newBillState.incomePercentsError)
            }
            if (newBillState.sumError != null) {
                sum.error = getString(newBillState.sumError)
            }
        })

        model.newBillResult.observe(viewLifecycleOwner, Observer {
            val newBillResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (newBillResult.error != null) {
                showNewBillFailed(newBillResult.error)
            }
            if (newBillResult.success) {
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.newBill_to_home)
            }

        })

        name.afterTextChanged {
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
            model.newBillDataChanged(
                name.text.toString(),
                incPercent,
                description.text.toString(),
                totalSum
            )
        }


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
                model.newBill(name.text.toString(), incPercent, description.text.toString(), totalSum)
            }
//        }
    }




    private fun showNewBillFailed(errorString: String) {
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