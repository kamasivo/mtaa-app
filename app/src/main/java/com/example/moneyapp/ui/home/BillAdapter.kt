package com.example.moneyapp.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moneyapp.R
import com.example.moneyapp.api.models.Bill

class BillAdapter: RecyclerView.Adapter<BillItemViewHolder>() {

    var data =  listOf<Bill>()

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: BillItemViewHolder, position: Int) {
        val item = data[position]
        Log.d("BillAdapter", item.name.toString())
        holder.textView.text = item.name.toString()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val view = layoutInflater.inflate(R.layout.bill_item_view, parent, false) as TextView

        return BillItemViewHolder(view)

    }
}