package com.example.moneyapp.ui.createincome

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moneyapp.GlobalApplication
import com.example.moneyapp.databinding.FragmentCreateIncomeBinding


class CreateIncome : Fragment(){
    private lateinit var model: CreateIncomeViewModel
    private var _binding: FragmentCreateIncomeBinding? = null
    private lateinit var spinner: Spinner
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateIncomeBinding.inflate(inflater, container, false)
        model = ViewModelProvider(this).get(CreateIncomeViewModel::class.java)
        val view = binding.root

        model.listOfBills.observe(viewLifecycleOwner, Observer {
            it?.let {

                val items: ArrayList<String> = ArrayList()

                for (i in it) {
                    Log.d("FragmentCreateIncome", i.name.toString())
                }
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    GlobalApplication.appContext!!,
                    android.R.layout.simple_spinner_item,
                    items
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)

                spinner.adapter = adapter
            }
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sum = binding.sumIncome
        val loading = binding.loading
        val create = binding.createIncome
        spinner = binding.incomeSource

        val model: CreateIncomeViewModel by viewModels()

        model.createIncomeFormState.observe(viewLifecycleOwner, Observer {
            val createIncomeState = it ?: return@Observer

            // disable login button unless both username / password is valid
            create.isEnabled = createIncomeState.isDataValid


            create.isEnabled = createIncomeState.isDataValid

            if (createIncomeState.sumError != null) {
                sum.error = createIncomeState.sumError
            }

        })

        model.createIncomeResult.observe(viewLifecycleOwner, Observer {
            val createIncomeResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (createIncomeResult.error != null) {
                showCreateIncomeFailed(createIncomeResult.error)
            }
            if (createIncomeResult.success) {
            }

        })

        sum.afterTextChanged {
            model.createIncomeDataChanged(
                sum.text.toString().toInt()
            )
        }


        create.setOnClickListener {
            loading.visibility = View.VISIBLE
//            model.createIncome(sum.text.toString().toInt())
        }
    }

    private fun showCreateIncomeFailed(errorString: String) {
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