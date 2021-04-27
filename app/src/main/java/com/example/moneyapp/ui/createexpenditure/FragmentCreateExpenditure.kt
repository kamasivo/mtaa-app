package com.example.moneyapp.ui.createexpenditure

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
import com.example.moneyapp.databinding.FragmentCreateExpenditureBinding


class CreateExpenditure : Fragment() {
    private var _binding: FragmentCreateExpenditureBinding? = null

    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateExpenditureBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sum = binding.sumExpenditure
        val loading = binding.loading
        val create = binding.createExpenditure


        val model: CreateExpenditureViewModel by viewModels()


        model.createExpenditureFormState.observe(viewLifecycleOwner, Observer {
            val createExpenditureState = it ?: return@Observer

            // disable login button unless both username / password is valid
            create.isEnabled = createExpenditureState.isDataValid


            create.isEnabled = createExpenditureState.isDataValid

            if (createExpenditureState.sumError != null) {
                sum.error = getString(createExpenditureState.sumError)
            }

        })

        model.createExpenditureResult.observe(viewLifecycleOwner, Observer {
            val createExpenditureResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (createExpenditureResult.error != null) {
                showCreateExpenditureFailed(createExpenditureResult.error)
            }
            if (createExpenditureResult.success) {
            }

        })

        sum.afterTextChanged {
            model.createExpenditureDataChanged(
                    sum.text.toString().toInt()
            )
        }


        create.setOnClickListener {
            loading.visibility = View.VISIBLE
            model.createExpenditure(sum.text.toString().toInt())
        }
//        }
    }

    private fun showCreateExpenditureFailed(errorString: String) {
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
