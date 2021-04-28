package com.example.moneyapp.ui.create_income_category

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.moneyapp.R
import com.example.moneyapp.api.models.Bill
import com.example.moneyapp.api.models.Category

class IncomeCategoryAdapter: RecyclerView.Adapter<IncomeCategoryAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val incomeCategoryName: TextView = itemView.findViewById(R.id.incomeCategory_name)
        val delete: TextView = itemView.findViewById(R.id.delete)
    }

    var data =  listOf<Category>()

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources
        holder.incomeCategoryName.text = item.name.toString()
        holder.incomeCategoryName.id = item.id.toString().toInt()

//        holder.delete.setOnClickListener {
//            val navController = Navigation.findNavController(holder.itemView)
//            Log.d("IncomeCategoryAdapter", holder.incomeCategoryName.id.toString())
//            val action = HomeFragmentDirections.homeToBill(holder.incomeCategoryName.id)
//            navController.navigate(action)
//        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.income_category_item_view, parent, false)
        return ViewHolder(view)
    }
}