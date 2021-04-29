package com.example.moneyapp.ui.detail_bill

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.moneyapp.R
import com.example.moneyapp.api.models.Bill
import com.example.moneyapp.api.models.Transaction

class ExpenditureAdapter: RecyclerView.Adapter<ExpenditureAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sum: TextView = itemView.findViewById(R.id.expenditure_sum)
        val edit: TextView = itemView.findViewById(R.id.edit)
    }

    var data =  listOf<Transaction>()

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources
        holder.sum.text = item.sum.toString()
        holder.edit.id = item.id.toString().toInt()

        holder.edit.setOnClickListener {
            val navController = Navigation.findNavController(holder.itemView)
            Log.d("BillAdapter", holder.edit.id.toString())
            val action = DetailBillFragmentDirections.billToNavUpdateTransaction(holder.edit.id)
            navController.navigate(action)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.expenditure_item_view, parent, false)
        return ViewHolder(view)
    }
}