package com.example.moneyapp.ui.createincome

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moneyapp.GlobalApplication
import com.example.moneyapp.databinding.FragmentCreateIncomeBinding


class CreateIncome : Fragment(){
    private lateinit var model: CreateIncomeViewModel
    private var _binding: FragmentCreateIncomeBinding? = null
    private lateinit var spinnerIncome: Spinner
    private lateinit var spinnerBill: Spinner
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateIncomeBinding.inflate(inflater, container, false)
        model = ViewModelProvider(this).get(CreateIncomeViewModel::class.java)
        val view = binding.root
        model.loadBills()

        setObservers()
        return view
    }

    fun setObservers() {
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sum = binding.sumIncome
        val loading = binding.loading
        val create = binding.createIncome
        spinnerIncome = binding.incomeSource
        spinnerBill = binding.billChoose

        val model: CreateIncomeViewModel by viewModels()

        model.createIncomeFormState.observe(viewLifecycleOwner, Observer {
            val createIncomeState = it ?: return@Observer

            // disable login button unless both username / password is valid
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
            val billChoose: Item = spinnerBill.selectedItem as Item
            Log.d("billChoosen", billChoose.id.toString())
            loading.visibility = View.VISIBLE
//            model.createIncome(sum.text.toString().toInt())
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

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}