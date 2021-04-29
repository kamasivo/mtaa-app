package com.example.moneyapp.ui.category

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.moneyapp.R
import com.example.moneyapp.api.models.Category
import com.example.moneyapp.api.models.ExpenditureCategory
import com.example.moneyapp.api.services.CategoryService

class ExpenditureCategoryAdapter: RecyclerView.Adapter<ExpenditureCategoryAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val expenditureCategoryName: TextView = itemView.findViewById(R.id.Category_name)
        val delete: TextView = itemView.findViewById(R.id.delete)
    }

    var data =  listOf<ExpenditureCategory>()

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources
        holder.expenditureCategoryName.text = item.name.toString()
        holder.expenditureCategoryName.id = item.id.toString().toInt()

        holder.delete.setOnClickListener {
            deleteCategory(holder.expenditureCategoryName.id)
            val navController = Navigation.findNavController(holder.itemView)
            navController.navigate(R.id.action_type)
        }
    }
    fun deleteCategory(categoryId: Int) {
        Log.d("NewTypeViewModel", "newType initiated");
        val apiService = CategoryService()

        apiService.deleteCategory(categoryId) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.category_item_view, parent, false)
        return ViewHolder(view)
    }
}