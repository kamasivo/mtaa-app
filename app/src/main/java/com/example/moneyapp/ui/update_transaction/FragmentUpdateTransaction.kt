package com.example.moneyapp.ui.update_transaction

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.moneyapp.R
import com.example.moneyapp.databinding.EditTransactionBinding
import com.example.moneyapp.ui.detail_bill.DetailBillFragmentArgs
import com.example.moneyapp.ui.new_bill.NewBillViewModel

class UpdateTransaction : Fragment() {
    private var _binding: EditTransactionBinding? = null
    private val binding get() = _binding!!
    val args: UpdateTransactionArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EditTransactionBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sum = binding.sumUpdate
        val update = binding.updateTransaction
        val delete = binding.deleteTransaction

        val model: UpdateTransactionViewModel by viewModels()

        model.newBillResult.observe(viewLifecycleOwner, Observer {
            val newBillResult = it ?: return@Observer

            if (newBillResult.error != null) {
                showNewBillFailed(newBillResult.error)
            }
            if (newBillResult.success) {
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.nav_home)
            }

        })

        update.setOnClickListener {
            var totalSum = 0
            try {
                totalSum = sum.text.toString().toInt()
            } catch (e: NumberFormatException) {
                // handle the exception
                totalSum = 0
            }
            model.updateTransaction(totalSum.toDouble(),args.transactionId)
        }

        delete.setOnClickListener {
            model.deleteTransaction(args.transactionId)
        }
    }

    private fun showNewBillFailed(errorString: String) {
        Toast.makeText(this.context, errorString, Toast.LENGTH_SHORT).show()
    }
}
