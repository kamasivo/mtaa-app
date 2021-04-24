package com.example.moneyapp.ui.newbill

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.moneyapp.NavigationActivity
import com.example.moneyapp.databinding.FragmentNewBillBinding



class NewBill : Fragment() {
    private var _binding: FragmentNewBillBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewBillBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
        // Inflate the layout for this fragment
 //       return inflater.inflate(R.layout.fragment_new_bill, container, false)
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
                Log.d("FragmentNewBillScreen", "Going to main screen")
                updateUiWithBill()
                //                setResult(Activity.RESULT_OK)
                //                finish()
            }

        })

        name.afterTextChanged {
            model.newBillDataChanged(
                    name.text.toString(),
                    incomePercents.text.toString().toInt(),
                    description.text.toString(),
                    sum.text.toString().toInt()
            )
        }

        incomePercents.afterTextChanged {
            model.newBillDataChanged(
                    name.text.toString(),
                    incomePercents.text.toString().toInt(),
                    description.text.toString(),
                    sum.text.toString().toInt()
            )
        }

        description.afterTextChanged {
            model.newBillDataChanged(
                    name.text.toString(),
                    incomePercents.text.toString().toInt(),
                    description.text.toString(),
                    sum.text.toString().toInt()
            )
        }

        sum.apply {
            afterTextChanged {
                model.newBillDataChanged(
                        name.text.toString(),
                        incomePercents.text.toString().toInt(),
                        description.text.toString(),
                        sum.text.toString().toInt()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        model.newBill(
                                name.text.toString(),
                                incomePercents.text.toString().toInt(),
                                description.text.toString(),
                                sum.text.toString().toInt()
                        )
                }
                false
            }

            create.setOnClickListener {
                loading.visibility = View.VISIBLE
                model.newBill(name.text.toString(), incomePercents.text.toString().toInt(), description.text.toString(), sum.text.toString().toInt())
            }
        }
    }




    private fun updateUiWithBill() {
        // redirect to main page
        val ide = Intent(this.context, NavigationActivity::class.java)
        startActivity(ide)
    }

    private fun showNewBillFailed(@StringRes errorString: Int) {
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