package com.example.moneyapp.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.moneyapp.R
import com.example.moneyapp.api.models.Bill

class BillAdapter: RecyclerView.Adapter<BillAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val billName: TextView = itemView.findViewById(R.id.bill_name)
        val sum: TextView = itemView.findViewById(R.id.sum)
        val edit: TextView = itemView.findViewById(R.id.edit)
    }

    var data =  listOf<Bill>()

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources
        holder.billName.text = item.name.toString()
        holder.sum.text = item.sum.toString()
        holder.billName.id = item.id.toString().toInt()

        holder.edit.setOnClickListener {
            val navController = Navigation.findNavController(holder.itemView)
            Log.d("BillAdapter", holder.billName.id.toString())
            val action = HomeFragmentDirections.homeToBill(holder.billName.id)
            navController.navigate(action)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.bill_item_view, parent, false)
        return ViewHolder(view)
    }
}